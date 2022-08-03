package info.firozansari.notes.presentation.notelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import info.firozansari.notes.domain.GetNoteListUseCase

class NoteListViewModelFactory(
    private val initialNoteListState: NoteListState?,
    private val noteListUseCase: GetNoteListUseCase
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NoteListViewModel(initialNoteListState, noteListUseCase) as T
    }
}