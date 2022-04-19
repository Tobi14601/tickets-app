package tk.t0bi.tickets.utils

import nl.komponents.kovenant.Dispatcher
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CachedDispatcher: Dispatcher {

    companion object {

        private const val THREAD_NAME = "Worker-"
        private var threads = 0

        private val executor: ExecutorService = Executors.newCachedThreadPool {
            val name = THREAD_NAME + ++threads
            val thread = Thread(it, name)
            thread.isDaemon = true
            thread.priority = android.os.Process.THREAD_PRIORITY_BACKGROUND
            thread
        }

    }

    override fun offer(task: () -> Unit): Boolean {
        executor.submit(task)
        return true
    }


}