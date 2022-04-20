package tk.t0bi.tickets.ui.event.list

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nl.komponents.kovenant.ui.failUi
import nl.komponents.kovenant.ui.successUi
import tk.t0bi.tickets.TAG
import tk.t0bi.tickets.data.repository.api.models.EventListItemModel
import tk.t0bi.tickets.data.repository.RepositoryServiceLocator
import tk.t0bi.tickets.utils.Event
import tk.t0bi.tickets.utils.UpdatableLiveData
import java.lang.Exception

class EventListViewModel: ViewModel() {

    val isLoading = ObservableBoolean()

    val events = UpdatableLiveData<List<EventListItemModel>>()

    val errorLiveData = MutableLiveData<Event<Exception>>()

    fun loadEvents() {
        isLoading.set(true)
        RepositoryServiceLocator.eventsRepository.getAllEvents().promise.successUi {
            events.value = it
            isLoading.set(false)
        }.failUi {
            Log.e(TAG, "loadEvents: Error loading events", it)
            errorLiveData.value = Event(it)
            isLoading.set(false)
        }
    }

    fun deleteEvent(event: EventListItemModel) {
        isLoading.set(true)
        RepositoryServiceLocator.eventsRepository.deleteEvent(event.id).promise.successUi {
            isLoading.set(false)
            loadEvents()
        }.failUi {
            Log.e(TAG, "deleteEvent: Error deleting event", it)
            isLoading.set(false)
            errorLiveData.value = Event(it)
        }
    }

}