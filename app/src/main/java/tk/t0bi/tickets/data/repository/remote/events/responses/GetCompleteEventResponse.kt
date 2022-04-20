package tk.t0bi.tickets.data.repository.remote.events.responses

import com.google.gson.annotations.SerializedName
import tk.t0bi.tickets.data.repository.api.models.CityModel
import tk.t0bi.tickets.data.repository.api.models.EventTicketsOverviewModel
import tk.t0bi.tickets.data.repository.api.models.TicketModel
import java.util.*

data class GetCompleteEventDto(
    @SerializedName("id")
    val id: Long,

    @SerializedName("title")
    val title: String,

    @SerializedName("date")
    val date: Date,

    @SerializedName("city")
    val city: GetCompleteEventCityDto,

    @SerializedName("tickets")
    val tickets: Array<GetCompleteEventTicketDto>
) {

    fun toEventTicketsOverviewModel(): EventTicketsOverviewModel {
        return EventTicketsOverviewModel(
            id,
            title,
            date,
            city.toCityModel(),
            tickets.map {
                it.toTicketModel()
            }.toTypedArray()
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GetCompleteEventDto

        if (id != other.id) return false
        if (title != other.title) return false
        if (date != other.date) return false
        if (city != other.city) return false
        if (!tickets.contentEquals(other.tickets)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + city.hashCode()
        result = 31 * result + tickets.contentHashCode()
        return result
    }
}

data class GetCompleteEventCityDto(
    @SerializedName("name")
    val name: String,

    @SerializedName("postCode")
    val postCode: String,

    @SerializedName("country")
    val country: String
) {
    fun toCityModel(): CityModel {
        return CityModel(name, postCode, country)
    }
}

data class GetCompleteEventTicketDto(
    @SerializedName("id")
    val id: Long,

    @SerializedName("barcode")
    val barcode: String,

    @SerializedName("firstName")
    val firstName: String,

    @SerializedName("lastName")
    val lastName: String,

    @SerializedName("available")
    val available: Boolean,

    @SerializedName("usedDate")
    val usedDate: Date?
) {

    fun toTicketModel(): TicketModel {
        return TicketModel(id, barcode, firstName, lastName, available, usedDate)
    }

}