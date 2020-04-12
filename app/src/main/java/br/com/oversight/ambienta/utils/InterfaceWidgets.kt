package br.com.oversight.ambienta.utils

import android.graphics.Color
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.google.android.material.snackbar.Snackbar
object InterfaceWidgets {
    @JvmStatic
    fun showSnack(view: View, msg: String) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show()
    }

//    @JvmStatic
//    fun showErrorDialog(errorMessage: String, fragmentManager: FragmentManager) {
//        DialogErrorSimpleText(errorMessage).show(fragmentManager, "mDialogError")
//    }
//
//    @JvmStatic
//    fun createDatePickerDialog(callBack: DatePickerDialog.OnDateSetListener, year: Int, monthOfYear: Int, dayOfMonth: Int): DatePickerDialog{
//        val pickerDialog = DatePickerDialog.newInstance(callBack, year, monthOfYear, dayOfMonth)
//        pickerDialog.accentColor = Color.parseColor("#62277F")
//        return pickerDialog
//    }
//
//    @JvmStatic
//    fun createDatePickerDialog(callBack: DatePickerDialog.OnDateSetListener): DatePickerDialog{
//        val pickerDialog = DatePickerDialog.newInstance(callBack)
//        pickerDialog.accentColor = Color.parseColor("#62277F")
//        return pickerDialog
//    }
}