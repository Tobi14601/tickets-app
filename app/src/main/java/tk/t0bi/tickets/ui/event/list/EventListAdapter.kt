package tk.t0bi.tickets.ui.event.list

import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import tk.t0bi.tickets.R
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
        view.menuButton.setOnClickListener {
            val popup = PopupMenu(it.context, it)
            popup.inflate(R.menu.menu_edit_delete)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.edit -> {
                        event?.let { event ->
                            callback.selectedEditEvent(event)
                        }
                        true
                    }
                    R.id.delete -> {
                        event?.let { event ->
                            callback.selectedDeleteEvent(event)
                        }
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }
    }

}