package tk.t0bi.tickets.data.repository.api.models

import androidx.annotation.StringRes
import tk.t0bi.tickets.R

data class EventEntryResultModel(
    val result: EventEntryResult,
    val ticket: TicketModel?
)

enum class EventEntryResult(@StringRes val message: Int) {

    ALLOW_ENTRY(R.string.entry_result_allow_entry),
    DENY_INVALID_CODE(R.string.entry_result_deny_invalid_code),
    DENY_ALREADY_ENTERED(R.string.entry_result_deny_already_entered),
    DENY_WRONG_DAY(R.string.entry_result_deny_wrong_day),
    UNKNOWN(R.string.entry_result_unknown)

}
