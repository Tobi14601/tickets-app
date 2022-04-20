package tk.t0bi.tickets.ui.ticket.edit

import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import org.androidannotations.annotations.*
import tk.t0bi.tickets.R
import tk.t0bi.tickets.data.repository.api.models.RepositoryError
import tk.t0bi.tickets.data.repository.api.models.RepositoryErrorException
import tk.t0bi.tickets.data.repository.api.models.TicketModel
import tk.t0bi.tickets.databinding.FragmentEditTicketBinding
import tk.t0bi.tickets.databinding.FragmentTicketEventOverviewBinding
import tk.t0bi.tickets.extensions.showError
import tk.t0bi.tickets.extensions.showSnackbar
import tk.t0bi.tickets.ui.ticket.overview.TicketEventOverviewViewModel
import java.util.regex.Pattern

@DataBound
@EFragment(R.layout.fragment_edit_ticket)
class EditTicketFragment : Fragment() {

    companion object {
        const val ARG_TICKET = "ticket"
        private val BARCODE_REGEX = Pattern.compile("^[a-zA-Z0-9]{0,8}\$")
    }

    private val viewModel: EditTicketViewModel by viewModels()

    @BindingObject
    protected lateinit var binding: FragmentEditTicketBinding

    @ViewById(R.id.firstNameEditText)
    protected lateinit var firstNameEditText: EditText

    @ViewById(R.id.lastNameEditText)
    protected lateinit var lastNameEditText: EditText

    @ViewById(R.id.barcodeEditText)
    protected lateinit var barcodeEditText: EditText

    @ViewById(R.id.toolbar)
    protected lateinit var toolbar: Toolbar

    var observersSetup = false

    @FragmentArg(ARG_TICKET)
    fun setTicketArg(model: TicketModel) {
        viewModel.initFromTicketModel(model)
    }

    @AfterViews
    fun setup() {
        setupDataBinding()
        setupToolbar()
        setupViewModelObservers()
    }

    fun setupDataBinding() {
        binding.editTicketViewModel = viewModel
        binding.lifecycleOwner = this
    }

    fun setupViewModelObservers() {
        if (observersSetup) {
            return
        }
        observersSetup = true

        viewModel.successLiveData.observe(this) {
            it.getContentIfNotHandledOrReturnNull()?.let {
                findNavController().popBackStack()
            }
        }
        viewModel.errorLiveData.observe(this) {
            it.getContentIfNotHandledOrReturnNull()?.let { error ->
                if (error is RepositoryErrorException) {
                    if (error.error == RepositoryError.CONFLICT) {
                        view?.showSnackbar(R.string.error_duplicate_barcode)
                        return@let
                    }
                }
                view?.showError(error)
            }
        }
    }

    fun setupToolbar() {
        toolbar.inflateMenu(R.menu.menu_edit_event)
        if (viewModel.ticketLiveData.value == null) {
            toolbar.setTitle(R.string.edit_ticket_title_create)
        } else {
            toolbar.setTitle(R.string.edit_ticket_title_edit)
        }

        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.save -> {
                    pressedSave()
                    true
                }
                else -> false
            }
        }

        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    fun pressedSave() {
        if (viewModel.firstNameLiveData.value.isNullOrBlank()) {
            firstNameEditText.error = getString(R.string.error_missing_field)
            firstNameEditText.requestFocus()
            return
        }
        if (viewModel.lastNameLiveData.value.isNullOrBlank()) {
            lastNameEditText.error = getString(R.string.error_missing_field)
            lastNameEditText.requestFocus()
            return
        }
        if (!BARCODE_REGEX.matcher(viewModel.barcodeLiveData.value ?: "").matches()) {
            barcodeEditText.error = getString(R.string.error_barcode_invalid)
            barcodeEditText.requestFocus()
            return
        }
        viewModel.saveTicket()
    }

}