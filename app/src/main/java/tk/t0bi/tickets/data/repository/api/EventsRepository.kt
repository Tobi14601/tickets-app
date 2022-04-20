package tk.t0bi.tickets.data.repository.api

import nl.komponents.kovenant.Deferred
import tk.t0bi.tickets.data.repository.api.models.EventListItemModel
import tk.t0bi.tickets.data.repository.api.models.SaveEventModel
import java.lang.Exception

interface EventsRepository {

    fun getAllEvents(): Deferred<List<EventListItemModel>, Exception>

    fun saveEvent(event: SaveEventModel): Deferred<Long, Exception>

}