package tk.t0bi.tickets.ui.event.list

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.androidannotations.annotations.*
import tk.t0bi.tickets.R
import tk.t0bi.tickets.TAG
import tk.t0bi.tickets.data.repository.api.models.EventListItemModel
import tk.t0bi.tickets.databinding.FragmentEventListBinding
import tk.t0bi.tickets.extensions.navigateSafe
import tk.t0bi.tickets.extensions.showError
import tk.t0bi.tickets.ui.event.edit.EventEditFragment
import tk.t0bi.tickets.ui.ticket.overview.TicketEventOverviewFragment

@DataBound
@EFragment(R.layout.fragment_event_list)
class EventListFragment : Fragment(), EventSelectedCallback {

    private val viewModel: EventListViewModel by viewModels()

    @BindingObject
    protected lateinit var binding: FragmentEventListBinding

    @ViewById(R.id.floatingActionButton)
    protected lateinit var floatingActionButton: FloatingActionButton

    @ViewById(R.id.eventList)
    protected lateinit var eventList: RecyclerView
    private val eventListAdapter: EventListAdapter by lazy {
        EventListAdapter(emptyList(), this)
    }

    var setupObservers = false

    @AfterViews
    fun setup() {
        setupEventsList()
        setupDataBinding()
        viewModel.loadEvents()
        setupViewModelObservers()

        floatingActionButton.setOnClickListener {
            openCreateEventFragment()
        }
    }

    fun setupViewModelObservers() {
        if (setupObservers) {
            updateEventList()
            return
        }
        setupObservers = true

        viewModel.events.observe(this) {
            updateEventList()
        }

        viewModel.errorLiveData.observe(this) {
            it.getContentIfNotHandledOrReturnNull()?.let { error ->
                view?.showError(error)
            }
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

    @SuppressLint("NotifyDataSetChanged")
    fun updateEventList() {
        viewModel.events.value?.let {
            eventListAdapter.events = it
            //warning ignored because we reload the entire list and don't have incremental updates
            eventListAdapter.notifyDataSetChanged()
        }
    }

    override fun eventSelected(event: EventListItemModel) {
        findNavController().navigateSafe(R.id.action_eventListFragment__to_ticketEventOverviewFragment_, Bundle().apply {
            putParcelable(TicketEventOverviewFragment.ARG_EVENT, event)
        })
    }

    override fun selectedEditEvent(event: EventListItemModel) {
        findNavController().navigateSafe(R.id.action_eventListFragment__to_eventEditFragment_, Bundle().apply {
            putParcelable(EventEditFragment.ARG_EDIT_EVENT, event)
        })
    }

    override fun selectedDeleteEvent(event: EventListItemModel) {
        context?.let {
            AlertDialog.Builder(it)
                .setTitle(R.string.event_list_confirm_delete_title)
                .setMessage(getString(R.string.event_list_confirm_delete_message, event.title))
                .setPositiveButton(R.string.button_confirm) { _, _ ->
                    viewModel.deleteEvent(event)
                }
                .setNegativeButton(R.string.button_cancel, null)
                .show()
        }
    }

    fun openCreateEventFragment() {
        findNavController().navigateSafe(R.id.action_eventListFragment__to_eventEditFragment_)
    }

}