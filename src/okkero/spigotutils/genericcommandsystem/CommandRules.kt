package okkero.spigotutils.genericcommandsystem

object ArgsSize {

    fun equal(value: Int): (ArgumentList) -> Boolean {
        return { it.size == value }
    }

}