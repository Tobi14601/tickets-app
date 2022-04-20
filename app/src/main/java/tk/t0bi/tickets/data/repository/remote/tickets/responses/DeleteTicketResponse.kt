package tk.t0bi.tickets.data.repository.remote.tickets.responses

import com.google.gson.annotations.SerializedName

data class DeleteTicketDto(
    @SerializedName("id")
    val id: Long
)