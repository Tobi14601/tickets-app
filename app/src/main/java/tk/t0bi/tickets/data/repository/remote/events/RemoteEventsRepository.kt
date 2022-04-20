package tk.t0bi.tickets.data.repository.remote.events

import nl.komponents.kovenant.Deferred
import nl.komponents.kovenant.deferred
import nl.komponents.kovenant.task
import tk.t0bi.tickets.data.repository.api.models.EventListItemModel
import tk.t0bi.tickets.data.repository.api.EventsRepository
import tk.t0bi.tickets.data.repository.api.models.SaveEventModel
import tk.t0bi.tickets.data.repository.remote.RetrofitServiceLocator
import tk.t0bi.tickets.data.repository.remote.events.bodies.SaveEventDto
import tk.t0bi.tickets.extensions.handleMapped
import java.lang.Exception

class RemoteEventsRepository: EventsRepository {
    override fun getAllEvents(): Deferred<List<EventListItemModel>, Exception> {
        val deferred = deferred<List<EventListItemModel>, Exception>()

        task {
            RetrofitServiceLocator.eventsRepository.getAllEvents().execute()
        }.handleMapped(deferred) {
            it.map { event ->
                EventListItemModel(event.id, event.title, event.city.name, event.city.postCode, event.city.country, event.date, event.totalTickets)
            }
        }

        return deferred
    }

    override fun saveEvent(event: SaveEventModel): Deferred<Long, Exception> {
        val deferred = deferred<Long, Exception>()

        task {
            if (event.id == null) {
                RetrofitServiceLocator.eventsRepository.createEvent(SaveEventDto(event)).execute()
            } else {
                RetrofitServiceLocator.eventsRepository.updateEvent(event.id, SaveEventDto(event)).execute()
            }
        }.handleMapped(deferred) {
            it.id
        }

        return deferred
    }

    override fun deleteEvent(eventId: Long): Deferred<Long, Exception> {
        val deferred = deferred<Long, Exception>()

        task {
            RetrofitServiceLocator.eventsRepository.deleteEvent(eventId).execute()
        }.handleMapped(deferred) {
            it.id
        }

        return deferred
    }
}