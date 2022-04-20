package tk.t0bi.tickets.ui.event.edit

import android.app.DatePickerDialog
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import org.androidannotations.annotations.*
import tk.t0bi.tickets.R
import tk.t0bi.tickets.data.repository.api.models.EventListItemModel
import tk.t0bi.tickets.databinding.FragmentEventEditBinding
import tk.t0bi.tickets.extensions.showError
import tk.t0bi.tickets.extensions.showSnackbar
import java.text.DateFormat
import java.util.*

@DataBound
@EFragment(R.layout.fragment_event_edit)
class EventEditFragment : Fragment() {

    companion object {
        const val ARG_EDIT_EVENT = "event"
    }

    private val viewModel: EventEditViewModel by viewModels()

    @BindingObject
    protected lateinit var binding: FragmentEventEditBinding

    @ViewById(R.id.toolbar)
    protected lateinit var toolbar: Toolbar

    @ViewById(R.id.titleEditText)
    protected lateinit var titleEditText: EditText

    @ViewById(R.id.changeDateButton)
    protected lateinit var dateButton: Button

    @ViewById(R.id.cityEditText)
    protected lateinit var cityEditText: EditText

    @ViewById(R.id.postCodeEditText)
    protected lateinit var postCodeEditText: EditText

    @ViewById(R.id.countryEditText)
    protected lateinit var countryEditText: EditText

    var observersSetup = false

    @FragmentArg(ARG_EDIT_EVENT)
    fun setEventArg(event: EventListItemModel) {
        viewModel.editEventLiveDate.value = event
        viewModel.cityLiveData.value = event.city
        viewModel.countryLiveData.value = event.country
        viewModel.dateLiveData.value = event.date
        viewModel.postCodeLiveData.value = event.postCode
        viewModel.titleLiveData.value = event.title
    }

    @AfterViews
    fun setup() {
        setupMenu()
        setupDataBinding()
        setupButtons()
        setupViewModelObservers()
        updateDatePickerButton()
    }

    fun setupViewModelObservers() {
        if (observersSetup) {
            updateDatePickerButton()
            return
        }
        observersSetup = true

        viewModel.dateLiveData.observe(this) {
            updateDatePickerButton()
        }

        viewModel.saveCompleteLiveData.observe(this) {
            it.getContentIfNotHandledOrReturnNull()?.let {
                findNavController().popBackStack()
            }
        }

        viewModel.errorLiveData.observe(this) {
            it.getContentIfNotHandledOrReturnNull()?.let { error ->
                view?.showError(error)
            }
        }
    }

    fun setupButtons() {
        dateButton.setOnClickListener {
            openDatePicker()
        }
    }

    fun setupDataBinding() {
        binding.eventEditViewModel = viewModel
        binding.lifecycleOwner = this
    }

    fun setupMenu() {
        toolbar.inflateMenu(R.menu.menu_edit_event)
        if (viewModel.editEventLiveDate.value == null) {
            toolbar.setTitle(R.string.create_event_title)
        } else {
            toolbar.setTitle(R.string.edit_event_title)
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

    fun updateDatePickerButton() {
        viewModel.dateLiveData.value?.let {
            dateButton.text = DateFormat.getDateInstance(DateFormat.MEDIUM).format(it)
        } ?: run {
            dateButton.setText(R.string.edit_event_date_placeholder)
        }
    }

    fun openDatePicker() {
        val date = viewModel.dateLiveData.value ?: Date()

        val calendar = Calendar.getInstance()
        calendar.time = date
        val dialog = DatePickerDialog(requireContext(), { _, year, month, day ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)

            if (viewModel.editEventLiveDate.value == null && !checkDateValid(calendar.time)) {
                //We are creating a event and the date is in the past
                view?.showSnackbar(R.string.error_date_in_past_not_allowed)
                return@DatePickerDialog
            }

            viewModel.dateLiveData.value = calendar.time
            updateDatePickerButton()
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
        dialog.show()
    }

    fun checkDateValid(date: Date): Boolean {
        val dateCalendar = Calendar.getInstance()
        dateCalendar.time = date
        dateCalendar.set(Calendar.HOUR_OF_DAY, 0)
        dateCalendar.set(Calendar.MINUTE, 0)
        dateCalendar.set(Calendar.SECOND, 0)
        dateCalendar.set(Calendar.MILLISECOND, 0)

        val currentCalendar = Calendar.getInstance()
        currentCalendar.set(Calendar.HOUR_OF_DAY, 0)
        currentCalendar.set(Calendar.MINUTE, 0)
        currentCalendar.set(Calendar.SECOND, 0)
        currentCalendar.set(Calendar.MILLISECOND, 0)

        return dateCalendar.timeInMillis == currentCalendar.timeInMillis
    }

    fun pressedSave() {
        if (viewModel.titleLiveData.value.isNullOrBlank()) {
            titleEditText.error = getString(R.string.error_missing_field)
            titleEditText.requestFocus()
            return
        }
        if (viewModel.dateLiveData.value == null) {
            view?.showSnackbar(R.string.error_missing_date)
            return
        }

        if (viewModel.cityLiveData.value.isNullOrBlank()) {
            cityEditText.error = getString(R.string.error_missing_field)
            cityEditText.requestFocus()
            return
        }
        if (viewModel.postCodeLiveData.value.isNullOrBlank()) {
            postCodeEditText.error = getString(R.string.error_missing_field)
            postCodeEditText.requestFocus()
            return
        }
        if (viewModel.countryLiveData.value.isNullOrBlank()) {
            countryEditText.error = getString(R.string.error_missing_field)
            countryEditText.requestFocus()
            return
        }

        viewModel.saveEvent()
    }

}