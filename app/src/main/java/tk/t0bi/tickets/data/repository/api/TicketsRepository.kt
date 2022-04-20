package tk.t0bi.tickets.data.repository.api

import nl.komponents.kovenant.Deferred
import tk.t0bi.tickets.data.repository.api.models.SaveTicketModel
import tk.t0bi.tickets.data.repository.api.models.SaveTicketResponse
import java.lang.Exception

interface TicketsRepository {

    fun saveTicket(model: SaveTicketModel): Deferred<SaveTicketResponse, Exception>

    fun deleteTicket(eventId: Long, ticketId: Long): Deferred<Long, Exception>

}