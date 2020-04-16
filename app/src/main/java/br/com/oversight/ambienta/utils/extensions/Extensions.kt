package br.com.oversight.ambienta.utils.extensions

import android.app.Activity
import android.app.DatePickerDialog
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import br.com.oversight.ambienta.ui.MainActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

val Any.TAG: String
    get() {
        val tag = javaClass.simpleName
        return if (tag.length <= 23) tag else tag.substring(0, 23)
    }

fun Fragment.showSnack(view: View, msg: String) {
    Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show()
}

fun Fragment.hideKeyboard() {
    (activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(activity!!.currentFocus?.windowToken,
        0
    )
}

fun Activity.hideKeyboard(activity: Activity) {
    (activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
        activity.currentFocus?.windowToken,
        0
    )
}

fun Fragment.vibrate(vibrator: Vibrator, duration: Long = 30) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
    else vibrator.vibrate(duration)
}

fun Date.toDateBrFormat(): String{
    return SimpleDateFormat("dd/MM/yyyy").format(this.time)
}
fun Date.toDateBrFormatWithHour(): String{
    return SimpleDateFormat("dd/MM/yyyy 'às' HH:mm").format(this.time)
}
fun Date.toCalendar(): Calendar {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar
}

fun Fragment.log(string: String){
    Log.d(this.TAG, string)
}