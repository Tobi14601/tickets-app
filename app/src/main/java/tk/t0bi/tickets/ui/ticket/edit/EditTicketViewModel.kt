package tk.t0bi.tickets.ui.ticket.edit

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tk.t0bi.tickets.data.repository.api.models.TicketModel
import tk.t0bi.tickets.utils.Event
import java.lang.Exception

class EditTicketViewModel: ViewModel() {

    val isLoading = ObservableBoolean()

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

    fun saveTicket() {

    }
}