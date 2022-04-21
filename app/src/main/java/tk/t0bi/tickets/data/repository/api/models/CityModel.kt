package tk.t0bi.tickets.data.repository.api.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CityModel(
    val name: String,
    val postCode: String,
    val country: String
) : Parcelable
