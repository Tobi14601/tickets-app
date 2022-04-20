package tk.t0bi.tickets.data.repository.remote.tickets

import retrofit2.Call
import retrofit2.http.*
import tk.t0bi.tickets.data.repository.remote.tickets.bodies.SaveTicketDto
import tk.t0bi.tickets.data.repository.remote.tickets.responses.DeleteTicketDto
import tk.t0bi.tickets.data.repository.remote.tickets.responses.SaveTicketResponseDto

interface RetrofitTicketsRepository {

    @POST("event/{eventId}/ticket")
    fun createTicket(
        @Path("eventId") eventId: Long,
        @Body ticket: SaveTicketDto
    ): Call<SaveTicketResponseDto>

    @PUT("event/{eventId}/ticket/{ticketId}")
    fun updateTicket(
        @Path("eventId") eventId: Long,
        @Path("ticketId") ticketId: Long,
        @Body ticket: SaveTicketDto
    ): Call<SaveTicketResponseDto>

    @DELETE("event/{eventId}/ticket/{ticketId}")
    fun deleteTicket(
        @Path("eventId") eventId: Long,
        @Path("ticketId") ticketId: Long
    ): Call<DeleteTicketDto>

}