package tk.t0bi.tickets.data.repository.api.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class EventTicketsOverviewModel(
    val id: Long,
    val title: String,
    val date: Date,
    val city: CityModel,
    val tickets: Array<TicketModel>
): Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EventTicketsOverviewModel

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
