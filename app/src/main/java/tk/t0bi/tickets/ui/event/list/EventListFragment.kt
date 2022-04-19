package tk.t0bi.tickets.ui.event.list

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.androidannotations.annotations.*
import tk.t0bi.tickets.R
import tk.t0bi.tickets.TAG
import tk.t0bi.tickets.data.EventListItemModel
import tk.t0bi.tickets.databinding.FragmentEventListBinding
import tk.t0bi.tickets.extensions.navigateSafe
import tk.t0bi.tickets.ui.event.edit.EventEditFragment
import java.util.*

@DataBound
@EFragment(R.layout.fragment_event_list)
class EventListFragment : Fragment(), EventSelectedCallback {

    private val viewModel: EventListViewModel by viewModels()

    @BindingObject
    protected lateinit var binding: FragmentEventListBinding

    @ViewById(R.id.eventList)
    protected lateinit var eventList: RecyclerView
    private val eventListAdapter: EventListAdapter by lazy {
        EventListAdapter(emptyList(), this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setupViewModelObservers()
        super.onCreate(savedInstanceState)
    }

    @AfterViews
    fun setup() {
        setupEventsList()
        setupDataBinding()
        viewModel.loadEvents()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setupViewModelObservers() {
        this.viewModel.events.observe(this) {
            Log.d(TAG, "setupViewModelObservers: events changed")
            eventListAdapter.events = it
            //warning ignored because we reload the entire list and don't have incremental updates
            eventListAdapter.notifyDataSetChanged()
        }
    }

    fun setupDataBinding() {
        binding.eventListViewModel = viewModel
    }

    fun setupEventsList() {
        eventList.adapter = eventListAdapter
        val layoutManager = LinearLayoutManager(context)
        eventList.layoutManager = layoutManager
    }

    override fun eventSelected(event: EventListItemModel) {
        Log.d(TAG, "eventSelected: $event")
        findNavController().navigateSafe(R.id.action_eventListFragment__to_eventEditFragment_, Bundle().apply {
            putParcelable(EventEditFragment.ARG_EDIT_EVENT, event)
        })
    }

}