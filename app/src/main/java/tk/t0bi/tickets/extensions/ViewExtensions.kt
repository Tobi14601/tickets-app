package tk.t0bi.tickets.extensions

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import tk.t0bi.tickets.R
import tk.t0bi.tickets.data.repository.api.models.RepositoryErrorException

fun View.showError(error: Throwable) {
    @StringRes var snackBarText: Int = R.string.error_gernal

    if (error is RepositoryErrorException) {
        snackBarText = error.error.description
    }

    val snackBar = Snackbar.make(this, snackBarText, Snackbar.LENGTH_LONG)

    snackBar.show()
}

fun View.showSnackbar(@StringRes id: Int) {
    Snackbar.make(this, id, Snackbar.LENGTH_LONG).show()
}

fun View.showSnackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}