package tk.t0bi.tickets.data.repository.remote.events

import retrofit2.Call
import retrofit2.http.GET
import tk.t0bi.tickets.data.repository.remote.events.responses.GetAllEventsDto

interface RetrofitEventsRepository {

    @GET("events")
    fun getAllEvents(): Call<Array<GetAllEventsDto>>

}