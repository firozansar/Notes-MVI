package info.firozansari.notes_mvvm.presentation.notelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import info.firozansari.notes_mvvm.R
import info.firozansari.notes_mvvm.domain.GetNoteListUseCase
import info.firozansari.notes_mvvm.domain.Note
import info.firozansari.notes_mvvm.presentation.notedetail.NoteDetailFragment
import kotlinx.android.synthetic.main.note_list.*

class NoteListFragment : Fragment() {

    private val clickListener: ClickListener = this::onNoteClicked

    private val recyclerViewAdapter = NoteAdapter(clickListener)

    companion object {
        fun newInstance() = NoteListFragment()
    }

    private lateinit var viewModel: NoteListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.note_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        // Normally ViewModelFactory should be injected here along with its UseCases injected into it
        viewModel = ViewModelProviders.of(this, NoteListViewModelFactory(null, GetNoteListUseCase()))
            .get(NoteListViewModel::class.java)

        viewModel.observableState.observe(viewLifecycleOwner, Observer { state ->
            state?.let { renderState(state) }
        })

        viewModel.dispatch(NoteListViewEvent.LoadNotes)
    }

    private fun renderState(noteListState: NoteListState) {
        with(noteListState) {
            when {
                isLoading -> renderLoadingState()
                isError -> renderErrorState()
                else -> renderNotesState(notes)
            }
        }
    }

    private fun renderLoadingState() {
        loadingIndicator.visibility = View.VISIBLE
    }

    private fun renderErrorState() {
        loadingIndicator.visibility = View.GONE
        Toast.makeText(requireContext(), R.string.error_loading_notes, Toast.LENGTH_LONG).show()
    }

    private fun renderNotesState(notes: List<Note>) {
        loadingIndicator.visibility = View.GONE
        recyclerViewAdapter.updateNotes(notes)
        notesRecyclerView.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        notesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        notesRecyclerView.adapter = recyclerViewAdapter
        notesRecyclerView.setHasFixedSize(true)
    }

    private fun onNoteClicked(note: Note) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, NoteDetailFragment.newInstance(note.id))
            .addToBackStack(null)
            .commit()
    }
}
