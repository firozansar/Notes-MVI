/*
* Copyright (C) 2019. WW International, Inc.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package info.firozansari.notes_mvvm.presentation.notedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import info.firozansari.notes_mvvm.domain.DeleteNoteUseCase
import info.firozansari.notes_mvvm.domain.GetNoteDetailUseCase

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