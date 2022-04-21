package tk.t0bi.tickets.data.repository.remote.events

import retrofit2.Call
import retrofit2.http.*
import tk.t0bi.tickets.data.repository.remote.events.bodies.SaveEventDto
import tk.t0bi.tickets.data.repository.remote.events.responses.EventEntryResultDto
import tk.t0bi.tickets.data.repository.remote.events.responses.GetAllEventsDto
import tk.t0bi.tickets.data.repository.remote.events.responses.GetCompleteEventDto
import tk.t0bi.tickets.data.repository.remote.events.responses.SaveEventResponseDto

interface RetrofitEventsRepository {

    @GET("events")
    fun getAllEvents(): Call<Array<GetAllEventsDto>>

    @GET("event/{eventId}")
    fun getEvent(
        @Path("eventId") eventId: Long,
    ): Call<GetCompleteEventDto>

    @POST("event")
    fun createEvent(
        @Body event: SaveEventDto
    ): Call<SaveEventResponseDto>

    @PUT("event/{eventId}")
    fun updateEvent(
        @Path("eventId") eventId: Long,
        @Body event: SaveEventDto
    ): Call<SaveEventResponseDto>

    @DELETE("event/{eventId}")
    fun deleteEvent(
        @Path("eventId") eventId: Long
    ): Call<SaveEventResponseDto>


    @PATCH("event/{eventId}/entry")
    fun eventEntry(
        @Path("eventId") eventId: Long,
        @Query("barcode") barcode: String
    ): Call<EventEntryResultDto>


}