package okkero.spigotutils.genericcommandsystem

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * A specific type of command that maps some or all of its arguments to instances of a given type.
 *
 * @param S the type of sender allowed to use this command
 * @param T the type to map arguments to
 * @property mapper takes an argument and returns an instance of [T]
 * @property elementCount the amount of elements this command will at least map. If the amount of arguments supplied by
 * the sender is less than this value, the command will throw an [IllegalCommandSyntaxException] upon execution.
 * @property optionalCount the amount of optional elements this command will try to map
 * @property greedy
 */
open class TypeCommand<S : CommandSender, T : Any>(senderClass: Class<S>, val mapper: (String) -> T?,
                                                   val elementCount: Int, val optionalCount: Int = 0,
                                                   val greedy: Boolean = false,
                                                   handler: (TypeCommandData<S, T>) -> CommandResult) :
        Command<S, TypeCommandData<S, T>>(senderClass, handler) {

    init {
        if (elementCount <= 0) {
            throw IllegalArgumentException("elementCount must be a positive integer")
        } else if (optionalCount !in 0..elementCount) {
            throw IllegalArgumentException("optionalCount must be between 0 and elmentCount inclusive")
        }
    }

    override fun buildData(args: ArgumentList.MutableDepth): TypeCommandData<S, T> {
        return TypeCommandData(args, mapper, elementCount, optionalCount, greedy)
    }

}

/**
 * Convenience "constructor" with reified type parameter for TypeCommand
 *
 * @param S the type of sender allowed to use this command
 * @param T the type to map arguments to
 * @param mapper takes an argument and returns an instance of [T]
 * @param elementCount the amount of elements this command will at least map. If the amount of arguments supplied by
 * the sender is less than this value, the command will throw an [IllegalCommandSyntaxException] upon execution.
 * @param optionalCount the amount of optional elements this command will try to map
 * @param greedy
 */
inline fun <reified S : CommandSender, T : Any>
        TypeCommand(noinline mapper: (String) -> T?,
                    elementCount: Int, optionalCount: Int = 0,
                    greedy: Boolean = false,
                    noinline handler: (TypeCommandData<S, T>) -> CommandResult): TypeCommand<S, T> {
    return TypeCommand(S::class.java, mapper, elementCount, optionalCount, greedy, handler)
}

//TODO Need to sit down and rethink the logic here; allow nulls or not??
open class TypeCommandData<S : CommandSender, out T : Any>(args: ArgumentList.MutableDepth,
                                                           mapper: (String) -> T?,
                                                           elementCount: Int, optionalCount: Int,
                                                           greedy: Boolean) : CommandData<S>(args) {

    private val elements = Array<Any?>(elementCount) { null }

    val lastOptionalIndex: Int
    val lastOptionalElement: T
        get() = getElement(lastOptionalIndex)
    val elementCount: Int
        get() = lastOptionalIndex + 1

    init {
        if (args.size < elementCount) {
            throw IllegalCommandSyntaxException()
        }

        val firstOptionalIndex = elementCount - optionalCount
        var lastOptionalIndex = elementCount - 1
        for (i in elements.indices) {
            val element = mapper(args[0])
            if (element == null && !greedy && i >= firstOptionalIndex) {
                lastOptionalIndex = i - 1
                break
            }
            elements[i] = element
            args.addDepth()
        }
        this.lastOptionalIndex = lastOptionalIndex
    }

    fun getElement(index: Int): T {
        if (!hasElement(index)) {
            throw IndexOutOfBoundsException("No mapped element at given index: $index")
        }
        return getOptionalElement(index) as T
    }

    fun getOptionalElement(index: Int): T? {
        return elements[index] as T?
    }

    fun hasElement(index: Int): Boolean {
        return index in 0..lastOptionalIndex
    }

}

/**
 * Convenience command that maps some or all arguments to [Player] instances.
 */
open class TargetCommand<S : CommandSender>(senderClass: Class<S>,
                                            elementCount: Int, optionalCount: Int = 0, greedy: Boolean = false,
                                            handler: (TypeCommandData<S, Player>) -> CommandResult) :
        TypeCommand<S, Player>(senderClass, { Bukkit.getPlayer(it) }, elementCount, optionalCount, greedy, handler)

/**
 * Convenience "constructor" with reified type parameter for TargetCommand
 */
inline fun <reified S : CommandSender>
        TargetCommand(elementCount: Int, optionalCount: Int = 0, greedy: Boolean = false,
                      noinline handler: (TypeCommandData<S, Player>) -> CommandResult): TargetCommand<S> {
    return TargetCommand(S::class.java, elementCount, optionalCount, greedy, handler)
}

/**
 * Convenience command that maps a single argument to a [Player] instance
 */
class SingleTargetCommand<S : CommandSender>(senderClass: Class<S>,
                                             handler: (SingleTargetCommandData<S>) -> CommandResult) :
        TargetCommand<S>(senderClass, 1, handler = handler as (TypeCommandData<S, Player>) -> CommandResult) {

    init {
        withRule(ArgsSize.equal(1))
    }

    override fun buildData(args: ArgumentList.MutableDepth): SingleTargetCommandData<S> {
        return SingleTargetCommandData(args)
    }

}

/**
 * Convenience "constructor" with reified type parameter for SingleTargetCommand
 */
inline fun <reified S : CommandSender>
        SingleTargetCommand(noinline handler: (SingleTargetCommandData<S>) -> CommandResult): SingleTargetCommand<S> {
    return SingleTargetCommand(S::class.java, handler)
}

class SingleTargetCommandData<S : CommandSender>(args: ArgumentList.MutableDepth) :
        TypeCommandData<S, Player>(args, { Bukkit.getPlayer(it) }, 1, 0, false) {

    val target: Player
        get() = getElement(0)

}