package tk.t0bi.tickets.data.repository.remote

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

data class ApiErrorResponse(
    @SerializedName("statusCode")
    val statusCode: Int,

    @SerializedName("message")
    val message: JsonElement
) {
    fun buildMessage(): String? {
        if (message.isJsonPrimitive) {
            return message.asJsonPrimitive.asString
        }

        if (message.isJsonArray) {
            return message.asJsonArray.map {
                it.asString
            }.joinToString("\n")
        }

        return null
    }
}
