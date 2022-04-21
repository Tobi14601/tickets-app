package tk.t0bi.tickets.data.repository.remote.events.responses

import com.google.gson.annotations.SerializedName
import tk.t0bi.tickets.data.repository.api.models.EventEntryResult
import tk.t0bi.tickets.data.repository.api.models.EventEntryResultModel
import tk.t0bi.tickets.data.repository.api.models.TicketModel
import java.util.*

data class EventEntryResultDto(
    @SerializedName("result")
    val result: String,
    @SerializedName("ticket")
    val ticket: EventEntryTicketDto?
) {

    companion object {
        private val resultNameMap = mapOf(
            "ALLOW_ENTRY" to EventEntryResult.ALLOW_ENTRY,
            "DENY_INVALID_CODE" to EventEntryResult.DENY_INVALID_CODE,
            "DENY_ALREADY_ENTERED" to EventEntryResult.DENY_ALREADY_ENTERED,
            "DENY_WRONG_DAY" to EventEntryResult.DENY_WRONG_DAY,
        )
    }

    fun toEventEntryResultModel(): EventEntryResultModel {
        return EventEntryResultModel(
            resultNameMap[result] ?: EventEntryResult.UNKNOWN,
            ticket?.toTicketModel()
        )
    }
}

data class EventEntryTicketDto(
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
