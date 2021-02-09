package info.firozansari.notes_mvvm.arch

import androidx.lifecycle.LiveData

interface ViewModelContract<VE : ViewEvent, VS : ViewState> {
    fun onEvent(event: VE)
    fun viewState(): LiveData<VS>
}
