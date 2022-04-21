package tk.t0bi.tickets.data.repository.remote.tickets

import nl.komponents.kovenant.Deferred
import nl.komponents.kovenant.deferred
import nl.komponents.kovenant.task
import tk.t0bi.tickets.data.repository.api.TicketsRepository
import tk.t0bi.tickets.data.repository.api.models.SaveTicketModel
import tk.t0bi.tickets.data.repository.api.models.SaveTicketResponse
import tk.t0bi.tickets.data.repository.remote.RetrofitServiceLocator
import tk.t0bi.tickets.data.repository.remote.tickets.bodies.SaveTicketDto
import tk.t0bi.tickets.extensions.handleMapped

class RemoteTicketsRepository : TicketsRepository {

    override fun saveTicket(model: SaveTicketModel): Deferred<SaveTicketResponse, Exception> {
        val deferred = deferred<SaveTicketResponse, Exception>()

        task {
            if (model.ticketId == null) {
                RetrofitServiceLocator.ticketsRepository.createTicket(model.eventId, SaveTicketDto(model)).execute()
            } else {
                RetrofitServiceLocator.ticketsRepository.updateTicket(
                    model.eventId,
                    model.ticketId,
                    SaveTicketDto(model)
                ).execute()
            }
        }.handleMapped(deferred) {
            it.toSaveTicketResponse()
        }

        return deferred
    }

    override fun deleteTicket(eventId: Long, ticketId: Long): Deferred<Long, Exception> {
        val deferred = deferred<Long, Exception>()

        task {
            RetrofitServiceLocator.ticketsRepository.deleteTicket(eventId, ticketId).execute()
        }.handleMapped(deferred) {
            it.id
        }

        return deferred
    }
}