package tk.t0bi.tickets.ui.event.edit

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nl.komponents.kovenant.ui.failUi
import nl.komponents.kovenant.ui.successUi
import tk.t0bi.tickets.TAG
import tk.t0bi.tickets.data.repository.RepositoryServiceLocator
import tk.t0bi.tickets.data.repository.api.models.EventListItemModel
import tk.t0bi.tickets.data.repository.api.models.SaveEventModel
import tk.t0bi.tickets.utils.Event
import java.util.*

class EventEditViewModel : ViewModel() {

    val editEventLiveDate = MutableLiveData<EventListItemModel>()
    val titleLiveData = MutableLiveData<String>()
    val dateLiveData = MutableLiveData<Date>()
    val cityLiveData = MutableLiveData<String>()
    val postCodeLiveData = MutableLiveData<String>()
    val countryLiveData = MutableLiveData<String>()

    val isLoading = ObservableBoolean()
    val errorLiveData = MutableLiveData<Event<Exception>>()
    val saveCompleteLiveData = MutableLiveData<Event<Boolean>>()

    fun saveEvent() {
        if (isLoading.get()) {
            //Make sure we aren't double saving
            return
        }

        val title = titleLiveData.value ?: return
        val date = dateLiveData.value ?: return
        val city = cityLiveData.value ?: return
        val postCode = postCodeLiveData.value ?: return
        val country = countryLiveData.value ?: return

        isLoading.set(true)
        RepositoryServiceLocator.eventsRepository.saveEvent(
            SaveEventModel(
                editEventLiveDate.value?.id,
                title,
                city,
                postCode,
                country,
                date
            )
        ).promise.successUi {
            isLoading.set(false)
            saveCompleteLiveData.value = Event(true)
        }.failUi {
            Log.e(TAG, "saveEvent: Error saving event", it)
            isLoading.set(false)
            errorLiveData.value = Event(it)
        }
    }

}