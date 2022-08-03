package info.firozansari.notes.presentation.notelist

import info.firozansari.notes.arch.ViewEvent

sealed class NoteListViewEvent : ViewEvent {
    object LoadNotes : NoteListViewEvent()
}