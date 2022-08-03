package info.firozansari.notes.arch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ViewModelFactory @Inject constructor(
    private val viewModelProviders: MutableMap<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(viewModelClass: Class<T>): T {
        val viewModelProvider = viewModelProviders[viewModelClass]
        val viewModel = viewModelProvider!!.get() as T
        return viewModel
    }
}
