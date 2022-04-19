package tk.t0bi.tickets.data.repository

import tk.t0bi.tickets.data.repository.api.EventsRepository
import tk.t0bi.tickets.data.repository.remote.events.RemoteEventsRepository

object RepositoryServiceLocator {

    val eventsRepository: EventsRepository by lazy {
        RemoteEventsRepository()
    }

}