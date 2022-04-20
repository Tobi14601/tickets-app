package tk.t0bi.tickets.data.repository.api

import nl.komponents.kovenant.Deferred
import tk.t0bi.tickets.data.repository.api.models.*
import java.lang.Exception

interface EventsRepository {

    fun getAllEvents(): Deferred<List<EventListItemModel>, Exception>

    fun getCompleteEvent(eventId: Long): Deferred<EventTicketsOverviewModel, Exception>

    fun saveEvent(event: SaveEventModel): Deferred<Long, Exception>

    fun deleteEvent(eventId: Long): Deferred<Long, Exception>

    fun entry(eventId: Long, code: String): Deferred<EventEntryResultModel, Exception>

}