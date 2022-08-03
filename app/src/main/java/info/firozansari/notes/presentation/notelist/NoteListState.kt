package info.firozansari.notes.presentation.notelist

import info.firozansari.notes.arch.ViewState
import info.firozansari.notes.domain.Note

data class NoteListState(
    val notes: List<Note> = listOf(),
    val isIdle: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false
) : ViewState