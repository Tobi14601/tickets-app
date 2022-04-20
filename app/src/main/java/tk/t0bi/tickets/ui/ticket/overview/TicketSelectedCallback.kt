package tk.t0bi.tickets.ui.ticket.overview

import tk.t0bi.tickets.data.repository.api.models.TicketModel

interface TicketSelectedCallback {

    fun editSelected(ticket: TicketModel)

    fun deleteSelected(ticket: TicketModel)

}