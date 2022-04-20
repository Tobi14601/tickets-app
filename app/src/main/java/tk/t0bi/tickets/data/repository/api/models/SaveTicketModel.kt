package tk.t0bi.tickets.data.repository.api.models

data class SaveTicketModel(
    val eventId: Long,
    val ticketId: Long?,
    val firstName: String,
    val lastName: String,
    val barcode: String
)
