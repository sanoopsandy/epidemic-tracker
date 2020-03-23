package `in`.tracker.core.utils

import `in`.tracker.core.api.DataResult
import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigator
import androidx.navigation.findNavController
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


/**
 * Function to push the loading status to the observing dataResult
 * */
fun <T : Any> MutableLiveData<DataResult<T>>.loading(isLoading: Boolean) {
    this.value = DataResult.loading(isLoading)
}

/**
 * Function to handle loading logic and push the value forward
 * */
fun <T : Any> MutableLiveData<DataResult<T>>.failure(e: Throwable) {
    this.value = DataResult.failure(e)
}

/**
 * Function to handle loading logic and push the value forward
 * */
fun <T : Any> MutableLiveData<DataResult<T>>.success(t: T) {
    this.value = DataResult.success(t)
}

fun ProgressBar.visibilityToggle(visible: Boolean) {
    this.visibility = if (visible)
        View.VISIBLE
    else
        View.GONE
}

fun Fragment.showToast(message: String) {
    Toast.makeText(this.context, message, Toast.LENGTH_LONG).show()
}

/**
 * Navigate to the destination and catch the exception
 */
fun NavController.safeNavigate(
    navDirections: NavDirections,
    navigatorExtras: Navigator.Extras? = null
) {
    try {
        navigatorExtras?.let { navigate(navDirections, it) } ?: navigate(navDirections)
    } catch (e: IllegalArgumentException) {
        println(e.message)
    }
}

fun View.navigateTo(
    navDirections: NavDirections,
    navigatorExtras: Navigator.Extras? = null
) {
    this.findNavController().safeNavigate(navDirections, navigatorExtras)
}

fun View.popBackStack() {
    this.findNavController().popBackStack()
}

fun Double.toAmountString(): String {
    val format = NumberFormat.getNumberInstance(Locale.getDefault()).format(this)
    return "₹ $format"
}

fun Int.toAmountString(): String {
    val format = NumberFormat.getNumberInstance(Locale.getDefault()).format(this)
    return "₹ $format"
}

fun Float.convertDpToPixel(context: Context): Float {
    return this * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

/**
 * Serves as an adapter to convert Network response to business logic models, Response need to
 * implement this
 * */
interface Mappable<out T : Any> {
    fun mapToResult(): DataResult<T>
}

fun String.getISTDateStringFromTime(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    val date = formatter.parse(this)
    return date?.let {
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        dateFormatter.timeZone = TimeZone.getTimeZone("Asia/Kolkata")
        dateFormatter.format(date)
    } ?: "INVALID"
}

fun String.getISTDateFromTime(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    val date = formatter.parse(this)
    return date?.let {
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        dateFormatter.timeZone = TimeZone.getTimeZone("Asia/Kolkata")
        dateFormatter.format(date)
    } ?: "INVALID"
}

fun ProgressBar.showProgress(loading: Boolean) {
    this.visibility = if (loading) View.VISIBLE else View.GONE
}