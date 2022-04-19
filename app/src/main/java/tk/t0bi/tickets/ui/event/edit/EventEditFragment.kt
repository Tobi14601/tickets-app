package tk.t0bi.tickets.ui.event.edit

import android.app.DatePickerDialog
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EFragment
import org.androidannotations.annotations.FragmentArg
import org.androidannotations.annotations.ViewById
import tk.t0bi.tickets.R
import tk.t0bi.tickets.TAG
import tk.t0bi.tickets.data.EventListItemModel
import java.text.DateFormat
import java.util.*

@EFragment(R.layout.fragment_event_edit)
class EventEditFragment : Fragment() {

    companion object {
        const val ARG_EDIT_EVENT = "event"
    }

    var event: EventListItemModel? = null

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

    private var selectedDate: Date? = null

    @FragmentArg(ARG_EDIT_EVENT)
    fun setEventArg(event: EventListItemModel) {
        this.event = event
        this.selectedDate = event.date
    }

    @AfterViews
    fun setup() {
        setupMenu()
        fillFromEditEvent()
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        dateButton.setOnClickListener {
            openDatePicker()
        }
    }

    fun setupMenu() {
        toolbar.inflateMenu(R.menu.menu_edit_event)
        if (event == null) {
            toolbar.setTitle(R.string.create_event_title)
        } else {
            toolbar.setTitle(R.string.edit_event_title)
        }

        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.save -> {
                    //TODO save
                    true
                }
                else -> false
            }
        }
    }

    fun fillFromEditEvent() {
        event?.let {
            titleEditText.setText(it.title)
            cityEditText.setText(it.city)
            postCodeEditText.setText(it.postCode)
            countryEditText.setText(it.country)
            updateDatePickerButton()
        }
    }

    fun updateDatePickerButton() {
        selectedDate?.let {
            dateButton.text = DateFormat.getDateInstance(DateFormat.MEDIUM).format(it)
        } ?: run {
            dateButton.setText(R.string.edit_event_date_placeholder)
        }
    }

    fun openDatePicker() {
        selectedDate?.let {

        }

        val date = selectedDate ?: Date()

        val calendar = Calendar.getInstance()
        calendar.time = date
        val dialog = DatePickerDialog(requireContext(), { datePicker, year, month, day ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            selectedDate = calendar.time
            updateDatePickerButton()
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
        dialog.show()
    }

}