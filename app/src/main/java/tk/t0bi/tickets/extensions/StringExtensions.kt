package tk.t0bi.tickets.extensions

import com.google.gson.Gson

private val GSON = Gson()

fun <T> String.tryFromJson(type: Class<T>): T? {
    return try {
        GSON.fromJson(this, type)
    } catch (error: Throwable) {
        null
    }
}