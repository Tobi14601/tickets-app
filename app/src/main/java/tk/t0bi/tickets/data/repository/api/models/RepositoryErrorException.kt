package tk.t0bi.tickets.data.repository.api.models

import java.lang.RuntimeException

class RepositoryErrorException(val error: RepositoryError, message: String): RuntimeException(message) {
}