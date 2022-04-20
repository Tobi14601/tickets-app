package tk.t0bi.tickets.data.repository.remote.tickets.responses

import com.google.gson.annotations.SerializedName
import tk.t0bi.tickets.data.repository.api.models.SaveTicketResponse

data class SaveTicketResponseDto(
    @SerializedName("id")
    val id: Long,

    @SerializedName("barcode")
    val barcode: String
) {
    fun toSaveTicketResponse(): SaveTicketResponse {
        return SaveTicketResponse(id, barcode)
    }
}