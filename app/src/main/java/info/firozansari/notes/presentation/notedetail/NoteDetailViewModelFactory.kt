package info.firozansari.notes.presentation.notedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import info.firozansari.notes.domain.DeleteNoteUseCase
import info.firozansari.notes.domain.GetNoteDetailUseCase

class NoteDetailViewModelFactory(
    private val initialNoteDetailViewState: NoteDetailViewState?,
    private val noteDetailUseCase: GetNoteDetailUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NoteDetailViewModel(initialNoteDetailViewState, noteDetailUseCase, deleteNoteUseCase) as T
    }
}