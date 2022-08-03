package info.firozansari.notes.domain

import info.firozansari.notes.data.NoteRepository
import io.reactivex.Single

class GetNoteListUseCase {
    fun loadAll(): Single<List<Note>> = Single.just(NoteRepository.loadAll())
}