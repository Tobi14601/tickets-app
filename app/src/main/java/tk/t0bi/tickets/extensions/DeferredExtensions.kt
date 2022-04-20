package tk.t0bi.tickets.extensions

import nl.komponents.kovenant.Deferred
import retrofit2.Response
import java.lang.RuntimeException

fun <V> Deferred<V, Exception>.handle(response: Response<V>) {
    response.body()?.let {
        this.resolve(it)
    } ?: run {
        this.reject(RuntimeException("No Body Received"))
    }
}

inline fun <V, T> Deferred<V, Exception>.handleMapped(
    response: Response<T>,
    mapper: (T) -> V
) {
    response.body()?.let {
        try {
            this.resolve(mapper(it))
        } catch (exception: Exception) {
            this.reject(exception)
        }
    } ?: run {
        this.reject(response.createException())
    }
}

fun <V> Deferred<V, Exception>.get(): V {
    return this.promise.get()
}