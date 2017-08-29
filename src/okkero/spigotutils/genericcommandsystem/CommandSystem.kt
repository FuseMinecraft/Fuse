package okkero.spigotutils.genericcommandsystem

import org.bukkit.ChatColor.RED
import org.bukkit.ChatColor.WHITE
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import java.util.*

/**
 * Base class for all commands. Subclass this to define a new kind of command. Do not instantiate/subclass if you want
 * to create a simple command. Use [SimpleCommand] for that.
 *
 * @param senderClass the class for the type of sender allowed to execute this command
 * @param handler the handler callback to be called when the command is executed
 * @param S the type of sender allowed to execute this command
 * @param D the type of data this command passes to the handler callback
 */
abstract class Command<S : CommandSender, D : CommandData<S>>(senderClass: Class<S>, handler: (D) -> CommandResult) :
        CommandExecutor {

    protected val senderClass = senderClass
    private val handler = handler

    private val rules = ArrayList<(ArgumentList) -> Boolean>()
    private val permissions = ArrayList<String>()
    private val subCommands = TreeMap<String, Command<out S, out CommandData<S>>>(String.CASE_INSENSITIVE_ORDER)

    /**
     * Add a rule to this command. If not all rules are met when executing the command, the execution will fail and the
     * sender will be notified. Returns the current instance, and thus can be chained.
     *
     * @param rule the rule to add
     * @return this instance
     */
    fun withRule(rule: (ArgumentList) -> Boolean): Command<S, D> {
        rules.add(rule)
        return this
    }

    /**
     * Add permissions to this command. If not all permissions are held by the sender upon execution, the execution will
     * fail and the sender will be notified. Returns the current instance, and thus can be chained.
     *
     * @param perms the permissions to add
     * @return this instance
     */
    fun withPermissions(vararg perms: String): Command<S, D> {
        permissions.addAll(perms)
        return this
    }

    /**
     * Add a sub command to this command. If the first argument to this command matches one of the aliases given to one
     * of the sub commands, then the execution of the command will be delegated to said sub command.
     * Returns the current instance, and thus can be chained.
     *
     * @param command the sub command to add
     * @param aliases the aliases associated with the sub command
     * @return this instance
     */
    fun withSubCommand(command: Command<out S, out CommandData<S>>, vararg aliases: String): Command<S, D> {
        for (alias in aliases) {
            subCommands[alias] = command
        }

        return this
    }

    internal fun execute(sender: CommandSender, args: ArgumentList.MutableDepth): CommandResult {
        if (!permissions.all { sender.hasPermission(it) }) {
            return CommandResult.MISSING_PERMS
        }
        if (!senderClass.isInstance(sender)) {
            return CommandResult.INVALID_SENDER_TYPE
        }
        sender as S

        if (!rules.all { it(args) }) {
            return CommandResult.INVALID_SYNTAX
        }

        if (args.size >= 1) {
            val subCommand = subCommands[args[0]]
            if (subCommand != null) {
                args.addDepth()
                return subCommand.execute(sender, args)
            }
        }

        return try {
            val data = buildData(args)
            data.sender = sender
            handler(data)
        } catch (e: IllegalCommandSyntaxException) {
            CommandResult.INVALID_SYNTAX
        } catch (e: Exception) {
            throw BadCommandException(e)
        }
    }

    /**
     * Construct the [CommandData] ([D]) to be passed to the handler callback, based on the given arguments.
     *
     * @param args the arguments supplied by the sender of the command
     * @return the command data
     */
    protected abstract fun buildData(args: ArgumentList.MutableDepth): D

    override fun onCommand(sender: CommandSender, cmd: org.bukkit.command.Command, alias: String?, args: Array<out String>): Boolean {
        val result = execute(sender, ArgumentList.MutableDepth(args))
        return when (result) {
            CommandResult.SUCCESS -> true
            CommandResult.MISSING_PERMS -> {
                sender.sendMessage("${RED}You do not have permission to use this command.")
                true
            }
            CommandResult.INVALID_SENDER_TYPE -> {
                sender.sendMessage("${RED}Only senders of type $WHITE${senderClass.simpleName}$RED may use this command.")
                true
            }
            CommandResult.INVALID_SYNTAX -> false
        }
    }

}

/**
 * A simple implementation of [Command]. This implementation merely wraps all arguments in a simple [CommandData]
 * object.
 *
 * @param senderClass the class for the type of sender allowed to execute this command
 * @param handler the handler callback to be called when the command is executed
 * @param S the type of sender allowed to execute this command
 */
open class SimpleCommand<S : CommandSender>(senderClass: Class<S>, handler: (CommandData<S>) -> CommandResult) :
        Command<S, CommandData<S>>(senderClass, handler) {

    override fun buildData(args: ArgumentList.MutableDepth) = CommandData<S>(args)

}

/**
 * Convenience "constructor" with reified type parameter for SimpleCommand
 *
 * @param handler the handler callback to be called when the command is executed
 * @param S the type of sender allowed to execute this command
 */
inline fun <reified S : CommandSender> SimpleCommand(noinline handler: (CommandData<S>) -> CommandResult): SimpleCommand<S> {
    return SimpleCommand(S::class.java, handler)
}

/**
 * Represents data to be passed to a command's handler callback. Contains information about an executed command.
 */
open class CommandData<S : CommandSender>(args: ArgumentList) {

    /**
     * The arguments passed by the sender of the command
     */
    val args: ArgumentList by lazy { ArgumentList.ImmutableDepth(args) }
    lateinit var sender: S
        internal set

}

/**
 * Represents a list of arguments passed by a command sender when executing a command. The depth defines the list's
 * entry point. The arguments that a command handler is expected to have use for under normal circumstances, are all the
 * arguments from the entry point to the end of the list.
 */
sealed class ArgumentList(private val args: Array<out String>) {

    /**
     * The offset from the entry point. A call to [get] will be offset by this value.
     */
    var depth = 0
        protected set
    /**
     * The amount of arguments in this list from the entry point
     */
    val size: Int
        get() = shallowSize - depth
    /**
     * The total amount of arguments in this list
     */
    val shallowSize = args.size

    /**
     * Get the argument at the given index, starting from the entry point
     */
    operator fun get(index: Int): String {
        return getShallow(index + depth)
    }

    /**
     * Get the argument at the given index, ignoring the entry point.
     */
    fun getShallow(index: Int): String {
        return args[index]
    }

    internal class ImmutableDepth(copyFrom: ArgumentList) : ArgumentList(copyFrom.args) {

        init {
            depth = copyFrom.depth
        }

    }

    class MutableDepth(args: Array<out String>) : ArgumentList(args) {

        //TODO not too sure about this KDoc
        /**
         * Add depth to this argument list. This is to be used when an argument is deemed little or not useful because
         * it has been processed to create a more meaningful alternative.
         */
        fun addDepth() {
            depth++
        }

    }

}

/**
 * Represents the result of executing a command. Command handlers are to return either of these to indicate the result
 * of executing the command.
 */
enum class CommandResult {

    /**
     * The command was successfully executed and processed.
     */
    SUCCESS,
    /**
     * The sender of the command used invalid syntax when executing the command. The sender will be notified and the
     * correct usage will be displayed.
     */
    INVALID_SYNTAX,
    /**
     * The sender of the command is of an unsupported type. For example, a [ConsoleCommandSender][org.bukkit.command.ConsoleCommandSender]
     * tried to execute a [SimpleCommand]<[Player][org.bukkit.entity.Player]>
     */
    INVALID_SENDER_TYPE,
    /**
     * The sender of the command does not have sufficient permissions to use the command.
     */
    MISSING_PERMS

}

/**
 * Thrown internally when a sender uses a command with invalid syntax. Command handlers should not throw this exception,
 * but instead return [CommandResult.INVALID_SYNTAX].
 */
class IllegalCommandSyntaxException : RuntimeException()

/**
 * Thrown if a command is badly implemented. This signifies an internal bug made by a developer, and not a mistake on
 * the sender's side. Consequently, this exception is never caught and will result in the sender being presented with
 * an error message.
 */
class BadCommandException(cause: Throwable) : RuntimeException(cause)