package info.firozansari.notes_mvvm.presentation.notelist

import androidx.recyclerview.widget.DiffUtil
import info.firozansari.notes_mvvm.domain.Note

class ItemDiffCallback(
    private val old: List<Note>,
    private val new: List<Note>
) : DiffUtil.Callback() {
    override fun getOldListSize() = old.size

    override fun getNewListSize() = new.size

    override fun areItemsTheSame(oldIndex: Int, newIndex: Int): Boolean {
        return old[oldIndex].text == new[newIndex].text
    }

    override fun areContentsTheSame(oldIndex: Int, newIndex: Int): Boolean {
        return old[oldIndex] == new[newIndex]
    }
}