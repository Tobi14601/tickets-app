package tk.t0bi.tickets.data.repository.api.models

class RepositoryErrorException(val error: RepositoryError, message: String) : RuntimeException(message) {
}