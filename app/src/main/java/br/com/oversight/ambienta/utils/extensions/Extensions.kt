package br.com.oversight.ambienta.utils.extensions

import android.app.Activity
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.oversight.ambienta.ui.MainActivity
import com.google.android.material.appbar.MaterialToolbar
import java.text.SimpleDateFormat
import java.util.*

val Any.TAG: String
    get() {
        val tag = javaClass.simpleName
        return if (tag.length <= 23) tag else tag.substring(0, 23)
    }

//fun Fragment.initToolbar(toolbar: MaterialToolbar, title: String , activity: MainActivity) {
//    val appCompatActivity = activity as AppCompatActivity
//    appCompatActivity.setSupportActionBar(toolbar)
//    appCompatActivity.supportActionBar?.title = title
//    appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
//}
//
//fun Fragment.initToolbar(toolbar: MaterialToolbar,  activity: MainActivity) {
//    initToolbar(toolbar, findNavController().currentDestination?.label.toString(),activity)
//}

//fun Fragment.setupNavDrawer(toolbar: MaterialToolbar, activity: MainActivity){
//    activity.setupNavDrawer(toolbar)
//}
//
//fun Fragment.enableNavDrawer(enable: Boolean, activity: MainActivity){
//    activity.enableNavDrawer(enable)
//}

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
    return SimpleDateFormat("dd.MM.yyyy").format(this.time)
}
fun Date.toDateBrFormatWithHour(): String{
    return SimpleDateFormat("dd.MM.yyyy 'às' HH:mm").format(this.time)
}
fun Date.toCalendar(): Calendar {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar
}