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
    initialNoteDetailViewState: NoteDetailViewState?,
    private val noteDetailUseCase: GetNoteDetailUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : BaseViewModel<NoteDetailViewEvent, NoteDetailViewState>() {

    override val initialState = initialNoteDetailViewState ?: NoteDetailViewState(isIdle = true)

    private val reducer: Reducer<NoteDetailViewState, NoteDetailViewStateChange> = { state, change ->
        when (change) {
            is NoteDetailViewStateChange.Loading -> state.copy(
                isLoading = true,
                note = null,
                isIdle = false,
                isLoadError = false,
                isDeleteError = false
            )
            is NoteDetailViewStateChange.NoteDetail -> state.copy(
                isLoading = false,
                note = change.note
            )
            is NoteDetailViewStateChange.NoteLoadError -> state.copy(
                isLoading = false,
                isLoadError = true
            )
            NoteDetailViewStateChange.NoteDeleted -> state.copy(
                isLoading = false,
                isNoteDeleted = true
            )
            is NoteDetailViewStateChange.NoteDeleteError -> state.copy(
                isLoading = false,
                isDeleteError = true
            )
        }
    }

    init {
        bindActions()
    }

    private fun bindActions() {
        val loadNoteChange = actions.ofType<NoteDetailViewEvent.LoadNoteDetail>()
            .switchMap { action ->
                noteDetailUseCase.findById(action.noteId)
                    .subscribeOn(Schedulers.io())
                    .toObservable()
                    .map<NoteDetailViewStateChange> { NoteDetailViewStateChange.NoteDetail(it) }
                    .onErrorReturn { NoteDetailViewStateChange.NoteLoadError(it) }
                    .startWith(NoteDetailViewStateChange.Loading)
            }

        val deleteNoteChange = actions.ofType<NoteDetailViewEvent.DeleteNote>()
            .switchMap { action ->
                noteDetailUseCase.findById(action.noteId)
                    .subscribeOn(Schedulers.io())
                    .flatMapCompletable { deleteNoteUseCase.delete(it) }
                    .toSingleDefault<NoteDetailViewStateChange>(NoteDetailViewStateChange.NoteDeleted)
                    .onErrorReturn { NoteDetailViewStateChange.NoteDeleteError(it) }
                    .toObservable()
                    .startWith(NoteDetailViewStateChange.Loading)
            }

        val allChanges = Observable.merge(loadNoteChange, deleteNoteChange)

        disposables += allChanges
            .scan(initialState, reducer)
            .filter { !it.isIdle && !it.isLoading }
            .distinctUntilChanged()
            .subscribe(state::postValue, Timber::e)
    }
}
