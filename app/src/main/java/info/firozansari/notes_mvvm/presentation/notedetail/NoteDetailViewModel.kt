package info.firozansari.notes_mvvm.presentation.notedetail

import info.firozansari.notes_mvvm.arch.BaseViewModel
import info.firozansari.notes_mvvm.arch.Reducer
import info.firozansari.notes_mvvm.domain.DeleteNoteUseCase
import info.firozansari.notes_mvvm.domain.GetNoteDetailUseCase
import io.reactivex.Observable
import io.reactivex.rxkotlin.ofType
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class NoteDetailViewModel(
    initialNoteDetailState: NoteDetailState?,
    private val noteDetailUseCase: GetNoteDetailUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : BaseViewModel<Action, NoteDetailState>() {

    override val initialState = initialNoteDetailState ?: NoteDetailState(isIdle = true)

    private val reducer: Reducer<NoteDetailState, Change> = { state, change ->
        when (change) {
            is Change.Loading -> state.copy(
                isLoading = true,
                note = null,
                isIdle = false,
                isLoadError = false,
                isDeleteError = false
            )
            is Change.NoteDetail -> state.copy(
                isLoading = false,
                note = change.note
            )
            is Change.NoteLoadError -> state.copy(
                isLoading = false,
                isLoadError = true
            )
            Change.NoteDeleted -> state.copy(
                isLoading = false,
                isNoteDeleted = true
            )
            is Change.NoteDeleteError -> state.copy(
                isLoading = false,
                isDeleteError = true
            )
        }
    }

    init {
        bindActions()
    }

    private fun bindActions() {
        val loadNoteChange = actions.ofType<Action.LoadNoteDetail>()
            .switchMap { action ->
                noteDetailUseCase.findById(action.noteId)
                    .subscribeOn(Schedulers.io())
                    .toObservable()
                    .map<Change> { Change.NoteDetail(it) }
                    .onErrorReturn { Change.NoteLoadError(it) }
                    .startWith(Change.Loading)
            }

        val deleteNoteChange = actions.ofType<Action.DeleteNote>()
            .switchMap { action ->
                noteDetailUseCase.findById(action.noteId)
                    .subscribeOn(Schedulers.io())
                    .flatMapCompletable { deleteNoteUseCase.delete(it) }
                    .toSingleDefault<Change>(Change.NoteDeleted)
                    .onErrorReturn { Change.NoteDeleteError(it) }
                    .toObservable()
                    .startWith(Change.Loading)
            }

        val allChanges = Observable.merge(loadNoteChange, deleteNoteChange)

        disposables += allChanges
            .scan(initialState, reducer)
            .filter { !it.isIdle && !it.isLoading }
            .distinctUntilChanged()
            .subscribe(state::postValue, Timber::e)
    }
}
