package tk.t0bi.tickets.ui.event.list

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import tk.t0bi.tickets.data.EventListItemModel
import tk.t0bi.tickets.utils.UpdatableLiveData

class EventListViewModel: ViewModel() {

    val isLoading = ObservableBoolean()

    val events = UpdatableLiveData<List<EventListItemModel>>()

    fun test() {

    }

}