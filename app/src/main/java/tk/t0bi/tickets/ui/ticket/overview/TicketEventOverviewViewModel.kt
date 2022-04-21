package tk.t0bi.tickets.ui.ticket.overview

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nl.komponents.kovenant.ui.failUi
import nl.komponents.kovenant.ui.successUi
import tk.t0bi.tickets.TAG
import tk.t0bi.tickets.data.repository.RepositoryServiceLocator
import tk.t0bi.tickets.data.repository.api.models.EventEntryResultModel
import tk.t0bi.tickets.data.repository.api.models.EventListItemModel
import tk.t0bi.tickets.data.repository.api.models.EventTicketsOverviewModel
import tk.t0bi.tickets.data.repository.api.models.TicketModel
import tk.t0bi.tickets.utils.Event

class TicketEventOverviewViewModel : ViewModel() {

    val isLoading = ObservableBoolean()
    val errorLiveData = MutableLiveData<Event<Exception>>()
    val eventLiveData = MutableLiveData<EventTicketsOverviewModel>()
    val entryLiveData = MutableLiveData<Event<EventEntryResultModel>>()

    fun initFromEventListModel(model: EventListItemModel) {
        eventLiveData.value = EventTicketsOverviewModel(model.id, model.title, model.date, model.city, emptyArray())
    }

    fun loadEvent() {
        eventLiveData.value?.let {
            isLoading.set(true)
            RepositoryServiceLocator.eventsRepository.getCompleteEvent(it.id).promise.successUi { model ->
                isLoading.set(false)
                eventLiveData.value = model
            }.failUi { error ->
                Log.e(TAG, "loadEvent: Error loading event", error)
                isLoading.set(false)
                errorLiveData.value = Event(error)
            }
        }
    }

    fun deleteTicket(ticket: TicketModel) {
        val event = eventLiveData.value ?: return

        isLoading.set(true)
        RepositoryServiceLocator.ticketsRepository.deleteTicket(event.id, ticket.id).promise
            .successUi {
                isLoading.set(false)
                loadEvent()
            }.failUi {
                isLoading.set(false)
                errorLiveData.value = Event(it)
            }
    }

    fun handleScannedBarcode(code: String) {
        val event = eventLiveData.value ?: return
        isLoading.set(true)
        RepositoryServiceLocator.eventsRepository.entry(event.id, code).promise.successUi {
            isLoading.set(false)
            entryLiveData.value = Event(it)
            loadEvent()
        }.failUi {
            Log.e(TAG, "handleScannedBarcode: Error checking entry", it)
            isLoading.set(false)
            errorLiveData.value = Event(it)
        }
    }

}