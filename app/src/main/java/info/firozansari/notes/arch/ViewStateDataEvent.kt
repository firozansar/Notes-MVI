package info.firozansari.notes.arch

data class ViewStateDataEvent<out T>(private val data: T) {

    private var consumed = false

    fun consume(): T? {
        return if (consumed) {
            null
        } else {
            consumed = true
            data
        }
    }

    fun consume(action: (T) -> Unit) {
        if (!consumed) {
            consumed = true
            action(data)
        }
    }

    fun peek(): T {
        return data
    }
}
