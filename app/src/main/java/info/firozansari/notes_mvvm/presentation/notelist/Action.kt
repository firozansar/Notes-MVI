package info.firozansari.notes_mvvm.presentation.notelist

import info.firozansari.notes_mvvm.arch.BaseAction

sealed class Action : BaseAction {
    object LoadNotes : Action()
}