package tk.t0bi.tickets.data.repository.api

import nl.komponents.kovenant.Deferred
import tk.t0bi.tickets.data.repository.api.models.SaveTicketModel
import tk.t0bi.tickets.data.repository.api.models.SaveTicketResponse

interface TicketsRepository {

    /**
     * Save the ticket. Creates a new Ticket when no ticketId is provided.
     * Will also create a random barcode when no barcode is provided
     *
     * @param model Ticket to save
     * @return Deferred resolving response containing id and barcode or and exception
     */
    fun saveTicket(model: SaveTicketModel): Deferred<SaveTicketResponse, Exception>

    /**
     * Deletes a single ticket identified by eventId and ticketId
     *
     * @param eventId Id of the event to delete a ticket from
     * @param ticketId Id of the ticket to delete
     * @return Deferred resolving id of the deleted ticket or an exception
     */
    fun deleteTicket(eventId: Long, ticketId: Long): Deferred<Long, Exception>

}