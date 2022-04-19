package tk.t0bi.tickets.ui.event.list

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import nl.komponents.kovenant.ui.failUi
import nl.komponents.kovenant.ui.successUi
import tk.t0bi.tickets.TAG
import tk.t0bi.tickets.data.EventListItemModel
import tk.t0bi.tickets.data.repository.RepositoryServiceLocator
import tk.t0bi.tickets.utils.UpdatableLiveData

class EventListViewModel: ViewModel() {

    val isLoading = ObservableBoolean()

    val events = UpdatableLiveData<List<EventListItemModel>>()

    fun loadEvents() {
        Log.d(TAG, "loadEvents: ")
        isLoading.set(true)
        RepositoryServiceLocator.eventsRepository.getAllEvents().promise.successUi {
            events.value = it
            isLoading.set(false)
        }.failUi {
            Log.e(TAG, "loadEvents: Error loading events", it)
            isLoading.set(false)
        }
    }

}