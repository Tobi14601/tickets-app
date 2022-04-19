package tk.t0bi.tickets.extensions

import nl.komponents.kovenant.Deferred
import nl.komponents.kovenant.Promise
import retrofit2.Response

inline fun <V, R> Promise<Response<R>, Exception>.handleMapped(
    deferred: Deferred<V, Exception>,
    crossinline mapper: (R) -> V
) {
    this success {
        deferred.handleMapped(it, mapper)
    } fail {
        deferred.reject(it)
    }
}