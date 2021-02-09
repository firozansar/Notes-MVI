package info.firozansari.notes_mvvm.presentation.notedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import info.firozansari.notes_mvvm.R
import info.firozansari.notes_mvvm.domain.DeleteNoteUseCase
import info.firozansari.notes_mvvm.domain.GetNoteDetailUseCase
import info.firozansari.notes_mvvm.domain.Note
import kotlinx.android.synthetic.main.note_detail.*

private const val NOTE_ID = "noteId"

class NoteDetailFragment : Fragment() {

    private val noteId by lazy {
        arguments?.getLong(NOTE_ID)
            ?: throw IllegalArgumentException("noteId is required")
    }

    companion object {
        fun newInstance(id: Long): NoteDetailFragment {
            val bundle = Bundle().apply {
                putLong(NOTE_ID, id)
            }
            return NoteDetailFragment().apply {
                arguments = bundle
            }
        }
    }

    private lateinit var viewModel: NoteDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.note_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Normally ViewModelFactory should be injected here along with its UseCases injected into it
        viewModel = ViewModelProviders.of(
            this,
            NoteDetailViewModelFactory(null, GetNoteDetailUseCase(), DeleteNoteUseCase())
        ).get(NoteDetailViewModel::class.java)

        viewModel.observableState.observe(this, Observer { state ->
            state?.let { renderState(state) }
        })

        if (savedInstanceState == null) {
            viewModel.dispatch(NoteDetailViewEvent.LoadNoteDetail(noteId))
        }

        deleteNoteButton.setOnClickListener {
            viewModel.dispatch(NoteDetailViewEvent.DeleteNote(noteId))
        }
    }

    private fun renderState(noteDetailViewState: NoteDetailViewState) {
        with(noteDetailViewState) {
            when {
                isLoadError -> renderLoadNoteDetailError()
                isDeleteError -> renderNoteDeleteError()
                note != null -> renderNoteDetailState(note)
                isNoteDeleted -> renderNoteDeleted()
            }
        }
    }

    private fun renderNoteDetailState(note: Note) {
        noteIdView.visibility = View.VISIBLE
        noteTextView.visibility = View.VISIBLE
        noteIdView.text = String.format(getString(R.string.note_detail_id), note.id)
        noteTextView.text = String.format(getString(R.string.note_detail_text), note.text)
    }

    private fun renderLoadNoteDetailError() {
        Toast.makeText(requireContext(), R.string.error_loading_note, Toast.LENGTH_LONG).show()
        noteIdView.visibility = View.GONE
        noteTextView.visibility = View.GONE
    }

    private fun renderNoteDeleteError() {
        Toast.makeText(requireContext(), R.string.error_deleting_note, Toast.LENGTH_LONG).show()
    }

    private fun renderNoteDeleted() {
        requireActivity().supportFragmentManager.popBackStack()
    }
}
