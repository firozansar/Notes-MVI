package info.firozansari.notes_mvvm.presentation.notedetail

import info.firozansari.notes_mvvm.arch.ViewEvent

sealed class NoteDetailViewEvent : ViewEvent {

    data class LoadNoteDetail(val noteId: Long) : NoteDetailViewEvent() {}
    data class DeleteNote(val noteId: Long) : NoteDetailViewEvent()
}