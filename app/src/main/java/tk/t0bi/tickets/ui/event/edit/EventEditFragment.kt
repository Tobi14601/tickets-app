package tk.t0bi.tickets.ui.event.edit

import android.util.Log
import androidx.fragment.app.Fragment
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EFragment
import org.androidannotations.annotations.FragmentArg
import tk.t0bi.tickets.R
import tk.t0bi.tickets.TAG
import tk.t0bi.tickets.data.EventListItemModel

@EFragment(R.layout.fragment_event_edit)
class EventEditFragment : Fragment() {

    companion object {
        const val ARG_EDIT_EVENT = "event"
    }

    @FragmentArg(ARG_EDIT_EVENT)
    @JvmField
    protected final var event: EventListItemModel? = null

    @AfterViews
    fun setup() {
        Log.d(TAG, "setup: $event")
    }

}