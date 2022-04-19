package tk.t0bi.tickets

import android.app.Application
import nl.komponents.kovenant.Kovenant
import nl.komponents.kovenant.android.androidUiDispatcher
import nl.komponents.kovenant.ui.KovenantUi
import org.androidannotations.annotations.EApplication
import tk.t0bi.tickets.utils.CachedDispatcher

@EApplication
class TicketsApp: Application() {

    override fun onCreate() {
        customStartConfigureKovenant()
        super.onCreate()
    }

    fun customStartConfigureKovenant() {
        KovenantUi.uiContext {
            dispatcher = androidUiDispatcher()
        }

        Kovenant.context {
            callbackContext {
                dispatcher = CachedDispatcher()
            }
            workerContext {
                dispatcher = CachedDispatcher()
            }
        }
    }

}