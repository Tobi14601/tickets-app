package tk.t0bi.tickets.ui.ticket.overview

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import org.androidannotations.annotations.*
import tk.t0bi.tickets.BARCODE_REGEX
import tk.t0bi.tickets.R
import tk.t0bi.tickets.data.repository.api.models.*
import tk.t0bi.tickets.databinding.FragmentTicketEventOverviewBinding
import tk.t0bi.tickets.extensions.navigateSafe
import tk.t0bi.tickets.extensions.showError
import tk.t0bi.tickets.ui.ticket.edit.EditTicketFragment
import java.text.DateFormat
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

    @ViewById(R.id.floatingActionButton)
    protected lateinit var floatingActionButton: FloatingActionButton

    var observersSetup = false

    val scannerResultLauncher = registerForActivityResult(ScanContract()) { result ->
        result.contents?.let {
            gotScanResult(it)
        }
    }

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

        floatingActionButton.setOnClickListener {
            pressedScan()
        }
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

        viewModel.entryLiveData.observe(this) {
            it.getContentIfNotHandledOrReturnNull()?.let { model ->
                showEntryResult(model)
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
            Bundle().apply {
                viewModel.eventLiveData.value?.let {
                    putParcelable(EditTicketFragment.ARG_EVENT, it)
                }
            }
        )
    }

    override fun editSelected(ticket: TicketModel) {
        findNavController().navigateSafe(
            R.id.action_ticketEventOverviewFragment__to_editTicketFragment_,
            Bundle().apply {
                viewModel.eventLiveData.value?.let {
                    putParcelable(EditTicketFragment.ARG_EVENT, it)
                }
                putParcelable(EditTicketFragment.ARG_TICKET, ticket)
            })
    }

    override fun deleteSelected(ticket: TicketModel) {
        context?.let {
            AlertDialog.Builder(it)
                .setTitle(R.string.event_overview_confirm_delete_title)
                .setMessage(
                    getString(
                        R.string.event_overview_confirm_delete_message,
                        ticket.firstName,
                        ticket.lastName
                    )
                )
                .setPositiveButton(R.string.button_confirm) { _, _ ->
                    viewModel.deleteTicket(ticket)
                }
                .setNegativeButton(R.string.button_cancel, null)
                .show()
        }
    }

    fun pressedScan() {
        if (!isEntryAllowedToday()) {
            context?.let {
                AlertDialog.Builder(it)
                    .setTitle(R.string.alert_attention)
                    .setMessage(R.string.error_entry_is_only_at_event_date_allowed)
                    .setPositiveButton(R.string.button_ok, null)
                    .show()
            }
            return
        }

        scannerResultLauncher.launch(ScanOptions())
    }

    fun gotScanResult(code: String) {
        if (!BARCODE_REGEX.matcher(code).matches()) {
            context?.let {
                AlertDialog.Builder(it)
                    .setTitle(R.string.alert_attention)
                    .setMessage(R.string.error_invalid_barcode_scanned)
                    .setPositiveButton(R.string.button_ok, null)
                    .show()
            }
            return
        }

        viewModel.handleScannedBarcode(code)
    }

    fun isEntryAllowedToday(): Boolean {
        val event = viewModel.eventLiveData.value ?: return false

        val dateCalendar = Calendar.getInstance()
        dateCalendar.time = event.date
        dateCalendar.set(Calendar.HOUR_OF_DAY, 0)
        dateCalendar.set(Calendar.MINUTE, 0)
        dateCalendar.set(Calendar.SECOND, 0)
        dateCalendar.set(Calendar.MILLISECOND, 0)

        val todayCalendar = Calendar.getInstance()
        todayCalendar.set(Calendar.HOUR_OF_DAY, 0)
        todayCalendar.set(Calendar.MINUTE, 0)
        todayCalendar.set(Calendar.SECOND, 0)
        todayCalendar.set(Calendar.MILLISECOND, 0)

        return dateCalendar.time == todayCalendar.time
    }

    fun showEntryResult(model: EventEntryResultModel) {
        context?.let {
            val message = model.ticket?.let { ticket ->
                getString(
                    R.string.entry_result_message_ticket,
                    getString(model.result.message),
                    ticket.firstName,
                    ticket.lastName,
                    if (ticket.available) getString(R.string.ticket_available) else getString(
                        R.string.ticket_not_available,
                        ticket.usedDate?.let { date ->
                            DateFormat.getDateInstance(DateFormat.FULL).format(date)
                        } ?: ""))
            } ?: getString(R.string.entry_result_message_no_ticket, getString(model.result.message))
            AlertDialog.Builder(it)
                .setTitle(if (model.result == EventEntryResult.ALLOW_ENTRY) R.string.entry_result_allowed else R.string.entry_result_denied)
                .setMessage(message)
                .setPositiveButton(R.string.button_ok, null)
                .show()
        }
    }

}