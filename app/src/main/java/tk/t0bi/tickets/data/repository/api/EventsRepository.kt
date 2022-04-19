package tk.t0bi.tickets.data.repository.api

import nl.komponents.kovenant.Deferred
import tk.t0bi.tickets.data.EventListItemModel
import java.lang.Exception

interface EventsRepository {

    fun getAllEvents(): Deferred<List<EventListItemModel>, Exception>

}