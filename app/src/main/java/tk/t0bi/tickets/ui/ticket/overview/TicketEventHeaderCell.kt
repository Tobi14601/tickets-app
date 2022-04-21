package tk.t0bi.tickets.ui.ticket.overview

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import org.androidannotations.annotations.EViewGroup
import org.androidannotations.annotations.ViewById
import tk.t0bi.tickets.R
import tk.t0bi.tickets.data.repository.api.models.EventTicketsOverviewModel
import java.text.DateFormat

@EViewGroup(R.layout.cell_event_header)
class TicketEventHeaderCell @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    @ViewById(R.id.cityTextView)
    protected lateinit var cityTextView: TextView

    @ViewById(R.id.postCodeTextView)
    protected lateinit var postCodeTextView: TextView

    @ViewById(R.id.dateTextView)
    protected lateinit var dateTextView: TextView

    fun bind(model: EventTicketsOverviewModel) {
        cityTextView.text = model.city.name
        postCodeTextView.text =
            context.getString(R.string.event_item_post_format, model.city.postCode, model.city.country)
        dateTextView.text = DateFormat.getDateInstance(DateFormat.MEDIUM).format(model.date)
    }

}