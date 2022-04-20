package tk.t0bi.tickets.data.repository.remote.events.responses

import com.google.gson.annotations.SerializedName

data class SaveEventResponseDto(
    @SerializedName("id")
    val id: Long
)