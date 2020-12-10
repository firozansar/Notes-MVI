package info.firozansari.notes_mvvm.presentation.notedetail

import info.firozansari.notes_mvvm.arch.BaseState
import info.firozansari.notes_mvvm.domain.Note

data class NoteDetailState(
    val note: Note? = null,
    val isIdle: Boolean = false,
    val isLoading: Boolean = false,
    val isLoadError: Boolean = false,
    val isNoteDeleted: Boolean = false,
    val isDeleteError: Boolean = false
) : BaseState