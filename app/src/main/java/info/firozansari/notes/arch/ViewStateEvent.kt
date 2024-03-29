package info.firozansari.notes.arch

data class ViewStateEvent(private var consumed: Boolean = false) {

    fun consume(action: () -> Unit) {
        if (!consumed) {
            consumed = true
            action()
        }
    }

    fun consumed() = consumed
}
