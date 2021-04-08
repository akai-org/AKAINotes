package pl.kossa.akainotes.extensions

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

fun makeCancelSnackbar(
    view: View,
    @StringRes question: Int,
    onCancel: () -> Unit,
    onTimeout: () -> Unit
){
    val snackbar = Snackbar.make(view, question, Snackbar.LENGTH_LONG)
    snackbar.setAction(android.R.string.cancel) {
        snackbar.dismiss()
        onCancel.invoke()
    }
    snackbar.addCallback(object : Snackbar.Callback() {

        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
            super.onDismissed(transientBottomBar, event)
            if (event == DISMISS_EVENT_TIMEOUT) {
                onTimeout.invoke()
            }
        }
    })
    snackbar.show()
}