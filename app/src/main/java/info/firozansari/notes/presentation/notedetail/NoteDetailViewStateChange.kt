package info.firozansari.notes.presentation.notedetail

import info.firozansari.notes.arch.ViewStateChange
import info.firozansari.notes.domain.Note

sealed class NoteDetailViewStateChange : ViewStateChange<NoteDetailViewState> {
    object Loading : NoteDetailViewStateChange() {
        override fun apply(currentViewState: NoteDetailViewState): NoteDetailViewState {
            return currentViewState.copy(
                isLoading = true
            )
        }
    }

    data class NoteDetail(val note: Note) : NoteDetailViewStateChange() {
        override fun apply(currentViewState: NoteDetailViewState): NoteDetailViewState {
            TODO("Not yet implemented")
        }
    }

    data class NoteLoadError(val throwable: Throwable?) : NoteDetailViewStateChange() {
        override fun apply(currentViewState: NoteDetailViewState): NoteDetailViewState {
            TODO("Not yet implemented")
        }
    }

    object NoteDeleted : NoteDetailViewStateChange() {
        override fun apply(currentViewState: NoteDetailViewState): NoteDetailViewState {
            TODO("Not yet implemented")
        }
    }

    data class NoteDeleteError(val throwable: Throwable?) : NoteDetailViewStateChange() {
        override fun apply(currentViewState: NoteDetailViewState): NoteDetailViewState {
            TODO("Not yet implemented")
        }
    }
}
