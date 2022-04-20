package tk.t0bi.tickets.data.repository.api.models

import androidx.annotation.StringRes
import tk.t0bi.tickets.R

enum class RepositoryError(@StringRes val description: Int) {

    INVALID_DATA(R.string.error_invalid_data),
    NOT_FOUND(R.string.error_not_found),
    CONFLICT(R.string.error_conflict),
    UNKNOWN(R.string.error_gernal)

}