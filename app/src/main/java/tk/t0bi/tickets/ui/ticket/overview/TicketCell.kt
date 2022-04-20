package tk.t0bi.tickets.ui.ticket.overview

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView
import org.androidannotations.annotations.EViewGroup
import org.androidannotations.annotations.ViewById
import tk.t0bi.tickets.R
import tk.t0bi.tickets.data.repository.api.models.TicketModel
import java.text.DateFormat

@EViewGroup(R.layout.cell_event_ticket)
class TicketCell @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    @ViewById(R.id.nameTextView)
    protected lateinit var nameTextView: TextView

    @ViewById(R.id.barcodeTextView)
    protected lateinit var barcodeTextView: TextView

    @ViewById(R.id.availableTextView)
    protected lateinit var availableTextView: TextView

    @ViewById(R.id.menuButton)
    lateinit var menuButton: TextView

    fun bind(model: TicketModel) {
        nameTextView.text = context.getString(R.string.ticket_cell_name_format, model.firstName, model.lastName)
        barcodeTextView.text = model.barcode
        if (model.available) {
            availableTextView.setText(R.string.ticket_available)
        } else {
            val dateString = model.usedDate?.let {
                DateFormat.getDateInstance(DateFormat.FULL).format(it)
            } ?: ""
            availableTextView.text = context.getString(R.string.ticket_not_available, dateString)
        }
    }

}