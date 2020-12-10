package info.firozansari.notes_mvvm.arch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import info.firozansari.notes_mvvm.arch.BaseAction
import info.firozansari.notes_mvvm.arch.BaseState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

/**
 * Store which manages business data and state.
 */
abstract class BaseViewModel<A : BaseAction, S : BaseState> : ViewModel() {
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
            setValue(data)
        }
    }

    /**
     * Dispatches an action. This is the only way to trigger a state change.
     */
    fun dispatch(action: A) {
        actions.onNext(action)
    }

    override fun onCleared() {
        disposables.clear()
    }
}