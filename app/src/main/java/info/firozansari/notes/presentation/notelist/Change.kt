package info.firozansari.notes.presentation.notelist

import info.firozansari.notes.domain.Note

sealed class Change {
    object Loading : Change()
    data class Notes(val notes: List<Note>) : Change()
    data class Error(val throwable: Throwable?) : Change()
}