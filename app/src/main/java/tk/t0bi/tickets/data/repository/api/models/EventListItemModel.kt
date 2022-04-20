package tk.t0bi.tickets.data.repository.api.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class EventListItemModel(
    val id: Long,
    val title: String,
    val city: CityModel,
    val date: Date,
    val ticketCount: Int
) : Parcelable
