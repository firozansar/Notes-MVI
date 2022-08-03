package info.firozansari.notes.arch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

abstract class BaseViewModel<A : ViewEvent, S : ViewState> : ViewModel() {

    protected val actions: PublishSubject<A> = PublishSubject.create<A>()

    protected val disposables: CompositeDisposable = CompositeDisposable()

    protected abstract val initialState: S

    protected val state = MutableLiveData<S>()

    private val tag by lazy { javaClass.simpleName }

    /**
     * Returns the current state. It is equal to the last value returned by the store's reducer.
     */
    val observableState: LiveData<S> = MediatorLiveData<S>().apply {
        addSource(state) { data ->
            Timber.d("$tag: Received state: $data")
            setValue(data)
        }
    }

    /**
     * Dispatches an action. This is the only way to trigger a state change.
     */
    fun dispatch(action: A) {
        Timber.d("$tag: Received action: $action")
        actions.onNext(action)
    }

    override fun onCleared() {
        disposables.clear()
    }
}
