package tk.t0bi.tickets.data.repository.api.models

import java.util.*

data class SaveEventModel(
    val id: Long?,
    val title: String,
    val city: String,
    val postCode: String,
    val country: String,
    val date: Date
)
