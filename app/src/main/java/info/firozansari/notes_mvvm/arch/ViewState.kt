package info.firozansari.notes_mvvm.arch

interface ViewState {

    fun obfuscatedString() = "${javaClass.simpleName}@${hashCode()}"
}