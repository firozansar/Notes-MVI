package info.firozansari.notes.arch

interface ViewState {

    fun obfuscatedString() = "${javaClass.simpleName}@${hashCode()}"
}