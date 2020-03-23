package `in`.tracker.core.prefs

import `in`.tracker.core.constants.COVID_PREFERENCE
import `in`.tracker.core.constants.LAST_UPDATED_TIME
import android.content.Context
import android.content.SharedPreferences

class CovidCorePref(private val context: Context) {

    /**
     * Variable has reference to the covid shared preferences
     */
    private val pref: SharedPreferences =
        context.getSharedPreferences(
            COVID_PREFERENCE,
            Context.MODE_PRIVATE
        )

    var lastUpdatedTime: String?
        get() = pref.getString(LAST_UPDATED_TIME, null)
        set(value) = pref.edit().putString(LAST_UPDATED_TIME, value).apply()

}