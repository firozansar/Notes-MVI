package info.firozansari.notes.presentation.notedetail

import info.firozansari.notes.arch.ViewState
import info.firozansari.notes.domain.Note

data class NoteDetailViewState (
    val note: Note? = null,
    val isIdle: Boolean = false,
    val isLoading: Boolean = false,
    val isLoadError: Boolean = false,
    val isNoteDeleted: Boolean = false,
    val isDeleteError: Boolean = false
) : ViewState