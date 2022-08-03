package info.firozansari.notes.presentation.notedetail

import info.firozansari.notes.arch.ViewEvent

sealed class NoteDetailViewEvent : ViewEvent {

    data class LoadNoteDetail(val noteId: Long) : NoteDetailViewEvent() {}
    data class DeleteNote(val noteId: Long) : NoteDetailViewEvent()
}