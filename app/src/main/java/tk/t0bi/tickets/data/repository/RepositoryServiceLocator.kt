package tk.t0bi.tickets.data.repository

import tk.t0bi.tickets.data.repository.api.EventsRepository
import tk.t0bi.tickets.data.repository.api.TicketsRepository
import tk.t0bi.tickets.data.repository.remote.events.RemoteEventsRepository
import tk.t0bi.tickets.data.repository.remote.tickets.RemoteTicketsRepository

object RepositoryServiceLocator {

    val eventsRepository: EventsRepository by lazy {
        RemoteEventsRepository()
    }

    val ticketsRepository: TicketsRepository by lazy {
        RemoteTicketsRepository()
    }

}