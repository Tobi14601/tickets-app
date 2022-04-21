package tk.t0bi.tickets.data.repository.api.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class TicketModel(
    val id: Long,
    val barcode: String,
    val firstName: String,
    val lastName: String,
    val available: Boolean,
    val usedDate: Date?
) : Parcelable
