package tk.t0bi.tickets.ui.ticket.edit

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nl.komponents.kovenant.ui.failUi
import nl.komponents.kovenant.ui.successUi
import tk.t0bi.tickets.data.repository.RepositoryServiceLocator
import tk.t0bi.tickets.data.repository.api.models.EventTicketsOverviewModel
import tk.t0bi.tickets.data.repository.api.models.SaveTicketModel
import tk.t0bi.tickets.data.repository.api.models.TicketModel
import tk.t0bi.tickets.utils.Event

class EditTicketViewModel : ViewModel() {

    val isLoading = ObservableBoolean()

    val eventLiveData = MutableLiveData<EventTicketsOverviewModel>()
    val ticketLiveData = MutableLiveData<TicketModel>()

    val firstNameLiveData = MutableLiveData<String>()
    val lastNameLiveData = MutableLiveData<String>()
    val barcodeLiveData = MutableLiveData<String>()

    val errorLiveData = MutableLiveData<Event<Exception>>()
    val successLiveData = MutableLiveData<Event<Boolean>>()

    fun initFromTicketModel(ticketModel: TicketModel) {
        ticketLiveData.value = ticketModel
        firstNameLiveData.value = ticketModel.firstName
        lastNameLiveData.value = ticketModel.lastName
        barcodeLiveData.value = ticketModel.barcode
    }

    fun initEventModel(eventModel: EventTicketsOverviewModel) {
        eventLiveData.value = eventModel
    }

    fun saveTicket() {
        val eventId = eventLiveData.value?.id ?: return
        val firstName = firstNameLiveData.value ?: return
        val lastName = lastNameLiveData.value ?: return
        val barcode = barcodeLiveData.value ?: ""

        isLoading.set(true)
        RepositoryServiceLocator.ticketsRepository.saveTicket(
            SaveTicketModel(
                eventId,
                ticketLiveData.value?.id,
                firstName,
                lastName,
                barcode
            )
        ).promise.successUi {
            isLoading.set(false)
            successLiveData.value = Event(true)
        }.failUi {
            isLoading.set(false)
            errorLiveData.value = Event(it)
        }
    }
}