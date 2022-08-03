package info.firozansari.notes.domain

import info.firozansari.notes.data.NoteRepository
import io.reactivex.Single

class GetNoteDetailUseCase {
    fun findById(id: Long): Single<Note> {
        return NoteRepository.findById(id)?.let { note ->
            Single.just(note)
        } ?: Single.error(IllegalArgumentException("Invalid note id passed in"))
    }
}