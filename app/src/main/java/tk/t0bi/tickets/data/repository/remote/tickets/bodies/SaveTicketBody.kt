package tk.t0bi.tickets.data.repository.remote.tickets.bodies

import com.google.gson.annotations.SerializedName
import tk.t0bi.tickets.data.repository.api.models.SaveTicketModel

data class SaveTicketDto(
    @SerializedName("firstName")
    val firstName: String,

    @SerializedName("lastName")
    val lastName: String,

    @SerializedName("barcode")
    val barcode: String
) {
    constructor(model: SaveTicketModel) : this(model.firstName, model.lastName, model.barcode)
}