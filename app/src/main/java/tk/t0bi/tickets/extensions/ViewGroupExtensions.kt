package tk.t0bi.tickets.extensions

import android.view.ViewGroup

fun <T : ViewGroup> T.setListLayoutParams(): T {
    this.layoutParams = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    return this
}