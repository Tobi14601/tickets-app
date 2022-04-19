package tk.t0bi.tickets.ui.event.list

import android.annotation.SuppressLint
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.androidannotations.annotations.*
import tk.t0bi.tickets.R
import tk.t0bi.tickets.TAG
import tk.t0bi.tickets.data.EventListItemModel
import tk.t0bi.tickets.databinding.FragmentEventListBinding
import java.util.*

@DataBound
@EFragment(R.layout.fragment_event_list)
class EventListFragment : Fragment(), EventSelectedCallback {

    private val viewModel: EventListViewModel by viewModels()

    @BindingObject
    protected lateinit var binding: FragmentEventListBinding

    @ViewById(R.id.eventList)
    protected lateinit var eventList: RecyclerView
    private var eventListAdapter: EventListAdapter? = null

    @AfterViews
    fun setup() {
        setupDataBinding()
        setupViewModelObservers()
        setupEventsList()

        viewModel.events.value = listOf(EventListItemModel("Test", "Schweinfurt", "97421", "DE", Date(), 12))
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setupViewModelObservers() {
        this.viewModel.events.observe(this) {
            eventListAdapter?.events = it
            //warning ignored because we reload the entire list and don't have incremental updates
            eventListAdapter?.notifyDataSetChanged()
        }
    }

    fun setupDataBinding() {
        binding.eventListViewModel = viewModel
    }

    fun setupEventsList() {
        eventListAdapter = EventListAdapter(emptyList(), this)
        eventList.adapter = eventListAdapter
        val layoutManager = LinearLayoutManager(context)
        eventList.layoutManager = layoutManager
    }

    override fun eventSelected(event: EventListItemModel) {
        Log.d(TAG, "eventSelected: $event")
    }

}