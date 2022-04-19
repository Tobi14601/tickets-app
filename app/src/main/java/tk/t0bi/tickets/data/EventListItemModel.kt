package tk.t0bi.tickets.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class EventListItemModel(
    val id: Long,
    val title: String,
    val city: String,
    val postCode: String,
    val country: String,
    val date: Date,
    val ticketCount: Int
) : Parcelable
