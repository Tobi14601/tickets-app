package tk.t0bi.tickets.data.repository.remote.events.responses

import com.google.gson.annotations.SerializedName
import tk.t0bi.tickets.data.repository.api.models.CityModel
import tk.t0bi.tickets.data.repository.api.models.EventListItemModel
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
) {
    fun toEventListItemModel(): EventListItemModel {
        return EventListItemModel(
            id,
            title,
            city.toCityModel(),
            date,
            totalTickets
        )
    }
}

data class GetAllEventsCityDto(
    @SerializedName("name")
    val name: String,

    @SerializedName("postCode")
    val postCode: String,

    @SerializedName("country")
    val country: String
) {
    fun toCityModel(): CityModel {
        return CityModel(
            name,
            postCode,
            country
        )
    }
}
