package info.firozansari.notes_mvvm.arch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel<VE : ViewEvent, VS : ViewState> : ViewModel(), ViewModelContract<VE, VS> {

    protected val subscriptions = CompositeDisposable()
    private val mutableViewState: MutableLiveData<VS> = MutableLiveData()

    protected fun updateViewState(vararg viewStateChanges: ViewStateChange<VS>) {
        var viewState: VS = requireCurrentViewState()
        for (viewStateChange in viewStateChanges) {
            viewState = viewStateChange.apply(viewState)
        }
        setViewState(viewState)
    }

    protected fun updateViewState(viewStateChange: (VS) -> VS) {
        val viewState = viewStateChange(requireCurrentViewState())
        setViewState(viewState)
    }

    protected fun currentViewState(): VS? {
        return mutableViewState.value
    }

    protected fun requireCurrentViewState(): VS {
        return requireNotNull(currentViewState()) {
            "currentViewState() was null. Have you set an initial view state?"
        }
    }

    protected fun setViewState(viewState: VS) {
        mutableViewState.value = viewState
    }

    override fun onEvent(event: VE) {}

    override fun viewState(): LiveData<VS> = mutableViewState

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }
}
