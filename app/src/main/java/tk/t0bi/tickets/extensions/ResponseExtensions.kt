package tk.t0bi.tickets.extensions

import retrofit2.Response
import tk.t0bi.tickets.data.repository.api.models.RepositoryError
import tk.t0bi.tickets.data.repository.api.models.RepositoryErrorException
import tk.t0bi.tickets.data.repository.remote.ApiErrorResponse

fun <T> Response<T>.createException(): Exception {

    if (this.body() == null) {
        return this.errorBody()?.let {
            val body = it.string()
            RepositoryErrorException(errorFromStatusCode(this.code()), body.tryFromJson(ApiErrorResponse::class.java)?.buildMessage() ?: "No message")
        } ?: run {
            RepositoryErrorException(RepositoryError.UNKNOWN, "No body Received")
        }
    }

    return RepositoryErrorException(RepositoryError.UNKNOWN, "No body Received")
}

private fun errorFromStatusCode(statusCode: Int): RepositoryError {
    return when (statusCode) {
        400 -> RepositoryError.INVALID_DATA
        404 -> RepositoryError.NOT_FOUND
        409 -> RepositoryError.CONFLICT
        else -> RepositoryError.UNKNOWN
    }
}