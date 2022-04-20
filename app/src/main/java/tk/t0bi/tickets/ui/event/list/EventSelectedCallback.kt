package tk.t0bi.tickets.ui.event.list

import tk.t0bi.tickets.data.repository.api.models.EventListItemModel

interface EventSelectedCallback {

    fun eventSelected(event: EventListItemModel)

    fun selectedEditEvent(event: EventListItemModel)

    fun selectedDeleteEvent(event: EventListItemModel)

}