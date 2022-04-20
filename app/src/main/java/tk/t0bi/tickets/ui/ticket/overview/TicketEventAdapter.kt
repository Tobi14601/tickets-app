package tk.t0bi.tickets.ui.ticket.overview

import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import tk.t0bi.tickets.R
import tk.t0bi.tickets.data.repository.api.models.EventTicketsOverviewModel
import tk.t0bi.tickets.data.repository.api.models.TicketModel
import tk.t0bi.tickets.extensions.setListLayoutParams

private const val TYPE_HEADER = 1
private const val TYPE_TICKET = 2

class TicketEventAdapter(var event: EventTicketsOverviewModel, val callback: TicketSelectedCallback) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return TYPE_HEADER
        }
        return TYPE_TICKET
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_HEADER) {
            val view = TicketEventHeaderCell_.build(parent.context)
            view.setListLayoutParams()
            return TicketEventHeaderViewHolder(view)
        }

        val view = TicketCell_.build(parent.context)
        view.setListLayoutParams()
        return TicketEventTicketViewHolder(view, null, callback)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == 0) {
            val parsedHolder = holder as TicketEventHeaderViewHolder
            parsedHolder.view.bind(event)
            return
        }

        val realIndex = position - 1
        val parsedHolder = holder as TicketEventTicketViewHolder
        val ticket = event.tickets[realIndex]
        parsedHolder.view.bind(ticket)
        parsedHolder.ticket = ticket
    }

    override fun getItemCount(): Int {
        return event.tickets.size + 1
    }
}

class TicketEventHeaderViewHolder(val view: TicketEventHeaderCell) : RecyclerView.ViewHolder(view) {

}

class TicketEventTicketViewHolder(
    val view: TicketCell,
    var ticket: TicketModel?,
    private val callback: TicketSelectedCallback
) : RecyclerView.ViewHolder(view) {

    init {
        view.menuButton.setOnClickListener {
            val popup = PopupMenu(it.context, it)
            popup.inflate(R.menu.menu_edit_delete)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.edit -> {
                        ticket?.let { ticket ->
                            callback.editSelected(ticket)
                        }
                        true
                    }
                    R.id.delete -> {
                        ticket?.let { ticket ->
                            callback.deleteSelected(ticket)
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