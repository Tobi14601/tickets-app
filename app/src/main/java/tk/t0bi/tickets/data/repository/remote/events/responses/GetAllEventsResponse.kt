package tk.t0bi.tickets.data.repository.remote.events.responses

import com.google.gson.annotations.SerializedName
import java.util.*

data class GetAllEventsDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("date")
    val date: Date,
    @SerializedName("city")
    val city: GetAllEventsCityDto,
    @SerializedName("totalTickets")
    val totalTickets: Int
)

data class GetAllEventsCityDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("postCode")
    val postCode: String,
    @SerializedName("country")
    val country: String
)
