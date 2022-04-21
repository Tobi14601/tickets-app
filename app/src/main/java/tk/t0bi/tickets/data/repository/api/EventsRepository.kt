package tk.t0bi.tickets.data.repository.api

import nl.komponents.kovenant.Deferred
import tk.t0bi.tickets.data.repository.api.models.EventEntryResultModel
import tk.t0bi.tickets.data.repository.api.models.EventListItemModel
import tk.t0bi.tickets.data.repository.api.models.EventTicketsOverviewModel
import tk.t0bi.tickets.data.repository.api.models.SaveEventModel

interface EventsRepository {

    /**
     * Gets a list of all events currently existing
     *
     * @return Deferred resolving List of all events or an exception
     */
    fun getAllEvents(): Deferred<List<EventListItemModel>, Exception>

    /**
     * Gets a single event identified by its id
     *
     * @param eventId Id of the event to get
     * @return Deferred resolving the event or an exception
     */
    fun getCompleteEvent(eventId: Long): Deferred<EventTicketsOverviewModel, Exception>

    /**
     * Saves the event. If no eventId is present a new event will be created.
     *
     * @param event Event to save
     * @return Deferred resolving the eventId or an exception
     */
    fun saveEvent(event: SaveEventModel): Deferred<Long, Exception>

    /**
     * Deletes a single event identified by its eventId
     *
     * @param eventId Id of the event to delete
     * @return Deferred resolving the deleted eventId or an exception
     */
    fun deleteEvent(eventId: Long): Deferred<Long, Exception>

    /**
     * Attempts an entry to the event. Event identified by the eventId. Ticket identified by the barcode
     *
     * @param eventId Id of the event to enter
     * @param code Barcode of the ticket the guest presented
     * @return Deferred resolving to event result or an exception
     */
    fun entry(eventId: Long, code: String): Deferred<EventEntryResultModel, Exception>

}