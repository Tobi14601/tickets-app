package tk.t0bi.tickets.ui.event.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tk.t0bi.tickets.data.repository.api.models.EventListItemModel
import tk.t0bi.tickets.extensions.setListLayoutParams

final class EventListAdapter(var events: List<EventListItemModel>, private val callback: EventSelectedCallback) :
    RecyclerView.Adapter<EventListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventListViewHolder {
        val view = EventListCell_.build(parent.context)
        view.setListLayoutParams()
        return EventListViewHolder(view, null, callback)
    }

    override fun onBindViewHolder(holder: EventListViewHolder, position: Int) {
        holder.event = events[position]
        holder.view.bind(events[position])
    }

    override fun getItemCount(): Int {
        return events.size
    }
}

class EventListViewHolder(
    val view: EventListCell,
    var event: EventListItemModel?,
    private var callback: EventSelectedCallback
) : RecyclerView.ViewHolder(view) {

    init {
        itemView.setOnClickListener {
            event?.let {
                callback.eventSelected(it)
            }
        }
    }

}