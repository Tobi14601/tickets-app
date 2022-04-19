package tk.t0bi.tickets.ui.event.list

import tk.t0bi.tickets.data.EventListItemModel

interface EventSelectedCallback {

    fun eventSelected(event: EventListItemModel)

}