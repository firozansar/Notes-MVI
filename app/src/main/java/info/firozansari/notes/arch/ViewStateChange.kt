package info.firozansari.notes.arch

interface ViewStateChange<VS : ViewState> {
    fun apply(currentViewState: VS): VS
}
