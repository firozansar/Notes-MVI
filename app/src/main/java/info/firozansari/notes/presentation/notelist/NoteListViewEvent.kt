package info.firozansari.notes_mvvm.presentation.notelist

import info.firozansari.notes_mvvm.arch.ViewEvent

sealed class NoteListViewEvent : ViewEvent {
    object LoadNotes : NoteListViewEvent()
}