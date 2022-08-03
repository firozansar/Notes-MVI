package info.firozansari.notes_mvvm.presentation.notelist

import info.firozansari.notes_mvvm.arch.ViewState
import info.firozansari.notes_mvvm.domain.Note

data class NoteListState(
    val notes: List<Note> = listOf(),
    val isIdle: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false
) : ViewState