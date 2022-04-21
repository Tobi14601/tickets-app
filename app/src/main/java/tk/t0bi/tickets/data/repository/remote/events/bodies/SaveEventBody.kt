package tk.t0bi.tickets.data.repository.remote.events.bodies

import com.google.gson.annotations.SerializedName
import tk.t0bi.tickets.data.repository.api.models.SaveEventModel
import java.util.*

data class SaveEventDto(
    @SerializedName("title")
    val title: String,

    @SerializedName("date")
    val date: Date,

    @SerializedName("city")
    val city: SaveEventCityDto
) {
    constructor(model: SaveEventModel) : this(
        model.title,
        model.date,
        SaveEventCityDto(model.city, model.postCode, model.country)
    )
}

data class SaveEventCityDto(
    @SerializedName("name")
    val name: String,

    @SerializedName("postCode")
    val postCode: String,

    @SerializedName("country")
    val country: String
)

