package pl.kossa.akainotes.prefs

import android.content.Context

class PrefsHelper(context: Context) {

    private val sharedPrefs =
        context.getSharedPreferences("pl.kossa.akainotes.prefs", Context.MODE_PRIVATE)

    var email: String?
        get() = sharedPrefs.getString(EMAIL, null)
        set(value) {
            val editor = sharedPrefs.edit()
            editor.putString(EMAIL, value)
            editor.apply()
        }

    var token: String?
        get() = sharedPrefs.getString(TOKEN, null)
        set(value) {
            val editor = sharedPrefs.edit()
            editor.putString(TOKEN, value)
            editor.apply()
        }

    companion object {
        private const val EMAIL = "EMAIL"
        private const val TOKEN = "TOKEN"
    }
}