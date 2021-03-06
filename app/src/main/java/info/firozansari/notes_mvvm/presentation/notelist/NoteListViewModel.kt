package info.firozansari.notes_mvvm.presentation.notelist

import info.firozansari.notes_mvvm.arch.BaseViewModel
import info.firozansari.notes_mvvm.arch.Reducer
import info.firozansari.notes_mvvm.domain.GetNoteListUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.ofType
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class NoteListViewModel(
    initialNoteDetailState: NoteListState?,
    private val loadNoteListUseCase: GetNoteListUseCase
) : BaseViewModel<NoteListViewEvent, NoteListState>() {

    override val initialState = initialNoteDetailState ?: NoteListState(isIdle = true)

    private val reducer: Reducer<NoteListState, Change> = { state, change ->
        when (change) {
            is Change.Loading -> state.copy(
                isIdle = false,
                isLoading = true,
                notes = emptyList(),
                isError = false
            )
            is Change.Notes -> state.copy(
                isLoading = false,
                notes = change.notes
            )
            is Change.Error -> state.copy(
                isLoading = false,
                isError = true
            )
        }
    }

    init {
        bindActions()
    }

    private fun bindActions() {
        val loadNotesChange = actions.ofType<NoteListViewEvent.LoadNotes>()
            .switchMap {
                loadNoteListUseCase.loadAll()
                    .subscribeOn(Schedulers.io())
                    .toObservable()
                    .map<Change> { Change.Notes(it) }
                    .defaultIfEmpty(Change.Notes(emptyList()))
                    .onErrorReturn { Change.Error(it) }
                    .startWith(Change.Loading)
            }

        // to handle multiple Changes, use Observable.merge to merge them into a single stream:
        // val allChanges = Observable.merge(loadNotesChange, ...)

        disposables += loadNotesChange
            .scan(initialState, reducer)
            .filter { !it.isIdle }
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(state::setValue, Timber::e)
    }
}
