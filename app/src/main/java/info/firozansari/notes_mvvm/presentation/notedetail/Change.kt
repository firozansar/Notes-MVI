package info.firozansari.notes_mvvm.presentation.notedetail

import info.firozansari.notes_mvvm.domain.Note

sealed class Change {
    object Loading : Change()
    data class NoteDetail(val note: Note) : Change()
    data class NoteLoadError(val throwable: Throwable?) : Change()
    object NoteDeleted : Change()
    data class NoteDeleteError(val throwable: Throwable?) : Change()
}
