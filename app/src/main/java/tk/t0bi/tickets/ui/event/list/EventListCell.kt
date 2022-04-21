package tk.t0bi.tickets.ui.event.list

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView
import org.androidannotations.annotations.EViewGroup
import org.androidannotations.annotations.ViewById
import tk.t0bi.tickets.R
import tk.t0bi.tickets.data.repository.api.models.EventListItemModel
import java.text.DateFormat

@EViewGroup(R.layout.cell_event)
class EventListCell @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    @ViewById(R.id.titleTextView)
    protected lateinit var titleTextView: TextView

    @ViewById(R.id.cityTextView)
    protected lateinit var cityTextView: TextView

    @ViewById(R.id.postCodeTextView)
    protected lateinit var postCodeTextView: TextView

    @ViewById(R.id.dateTextView)
    protected lateinit var dateTextView: TextView

    @ViewById(R.id.ticketsTextView)
    protected lateinit var ticketsTextView: TextView

    @ViewById(R.id.menuButton)
    lateinit var menuButton: TextView

    fun bind(model: EventListItemModel) {
        titleTextView.text = model.title
        cityTextView.text = model.city.name
        postCodeTextView.text =
            context.getString(R.string.event_item_post_format, model.city.postCode, model.city.country)
        dateTextView.text = DateFormat.getDateInstance(DateFormat.MEDIUM).format(model.date)
        ticketsTextView.text = context.getString(R.string.event_item_tickets_format, model.ticketCount)
    }

}