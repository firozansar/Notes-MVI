package info.firozansari.notes_mvvm.domain

import info.firozansari.notes_mvvm.data.NoteRepository
import io.reactivex.Single

class GetNoteListUseCase {
    fun loadAll(): Single<List<Note>> = Single.just(NoteRepository.loadAll())
}