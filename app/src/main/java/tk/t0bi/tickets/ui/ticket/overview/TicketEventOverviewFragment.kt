package tk.t0bi.tickets.ui.ticket.overview

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.androidannotations.annotations.*
import tk.t0bi.tickets.R
import tk.t0bi.tickets.data.repository.api.models.CityModel
import tk.t0bi.tickets.data.repository.api.models.EventListItemModel
import tk.t0bi.tickets.data.repository.api.models.EventTicketsOverviewModel
import tk.t0bi.tickets.data.repository.api.models.TicketModel
import tk.t0bi.tickets.databinding.FragmentTicketEventOverviewBinding
import tk.t0bi.tickets.extensions.navigateSafe
import tk.t0bi.tickets.extensions.showError
import tk.t0bi.tickets.ui.ticket.edit.EditTicketFragment
import tk.t0bi.tickets.ui.ticket.edit.EditTicketFragment_
import java.util.*

@DataBound
@EFragment(R.layout.fragment_ticket_event_overview)
class TicketEventOverviewFragment : Fragment(), TicketSelectedCallback {

    companion object {
        const val ARG_EVENT = "event"
    }

    private val viewModel: TicketEventOverviewViewModel by viewModels()

    @BindingObject
    protected lateinit var binding: FragmentTicketEventOverviewBinding

    @ViewById(R.id.contentList)
    protected lateinit var contentList: RecyclerView

    @ViewById(R.id.toolbar)
    protected lateinit var toolbar: Toolbar

    var observersSetup = false

    val adapter by lazy {
        TicketEventAdapter(
            viewModel.eventLiveData.value ?: EventTicketsOverviewModel(
                -1,
                "",
                Date(),
                CityModel("", "", ""),
                emptyArray()
            ),
            this
        )
    }

    @FragmentArg(ARG_EVENT)
    fun setEventArg(event: EventListItemModel) {
        viewModel.initFromEventListModel(event)
    }

    @AfterViews
    fun setup() {
        setupMenu()
        setupDataBinding()
        setupViewModelObservers()
        setupContentList()
        viewModel.loadEvent()
    }

    fun setupDataBinding() {
        binding.overviewViewModel = viewModel
    }

    fun setupContentList() {
        contentList.adapter = adapter
        val layoutManager = LinearLayoutManager(requireContext())
        contentList.layoutManager = layoutManager
    }

    fun setupMenu() {
        toolbar.inflateMenu(R.menu.menu_event_overview)

        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.add -> {
                    pressedAddTicket()
                    true
                }
                else -> false
            }
        }

        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    fun setupViewModelObservers() {
        if (observersSetup) {
            updateContent()
            return
        }
        observersSetup = true

        viewModel.eventLiveData.observe(this) {
            updateContent()
        }

        viewModel.errorLiveData.observe(this) {
            it.getContentIfNotHandledOrReturnNull()?.let { error ->
                view?.showError(error)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateContent() {
        viewModel.eventLiveData.value?.let {
            adapter.event = it
            //We only get an update that it changed not what changed so we need to update the enter list
            adapter.notifyDataSetChanged()
            toolbar.title = it.title
        }
    }

    fun pressedAddTicket() {
        findNavController().navigateSafe(
            R.id.action_ticketEventOverviewFragment__to_editTicketFragment_,
        )
    }

    override fun editSelected(ticket: TicketModel) {
        findNavController().navigateSafe(
            R.id.action_ticketEventOverviewFragment__to_editTicketFragment_,
            Bundle().apply {
                putParcelable(EditTicketFragment.ARG_TICKET, ticket)
            })
    }

    override fun deleteSelected(ticket: TicketModel) {
        TODO("Not yet implemented")
    }

}