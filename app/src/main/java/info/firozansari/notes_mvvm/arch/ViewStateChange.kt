package info.firozansari.notes_mvvm.arch

interface ViewStateChange<VS : ViewState> {
    fun apply(currentViewState: VS): VS
}
