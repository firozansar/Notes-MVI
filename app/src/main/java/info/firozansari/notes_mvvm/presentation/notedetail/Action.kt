package info.firozansari.notes_mvvm.presentation.notedetail

import info.firozansari.notes_mvvm.arch.BaseAction

sealed class Action : BaseAction {
    data class LoadNoteDetail(val noteId: Long) : Action() {
    }

    data class DeleteNote(val noteId: Long) : Action()
}