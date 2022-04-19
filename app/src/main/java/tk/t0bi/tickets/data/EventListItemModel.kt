package tk.t0bi.tickets.data

import java.util.*

data class EventListItemModel(
    val title: String,
    val city: String,
    val postCode: String,
    val country: String,
    val date: Date,
    val ticketCount: Int
)
