package br.com.oversight.ambienta.utils.extensions

import androidx.databinding.InverseMethod
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

object Converter {

    @InverseMethod("stringToInt")
    @JvmStatic
    fun intToString(value: Int): String {
        return "$value"
    }

    @JvmStatic
    fun stringToInt(value: String): Int {
        try {
            return Integer.parseInt(value.replace("\\D".toRegex(), ""))
        } catch (e: Exception) {
            e.printStackTrace()
            return 0
        }
    }

    @JvmStatic
    fun floatToCurrency(value: Float?): String {
        return String.format("R$ %.2f", value ?: 0f)
    }

    @JvmStatic
    fun floatToCurrencyWithFreeState(value: Float?): String {
        if (value == 0f) return "Grátis"
        return String.format("R$ %.2f", value ?: 0f)
    }

    @JvmStatic
    fun dateToDateBrFormat(date: Date): String = date.toDateBrFormat()

    @JvmStatic
    fun dateToPattern(date: Date, pattern: String): String = SimpleDateFormat(pattern).format(date)

    @JvmStatic
    fun dateToDateBrFormatWithHour(date: Date): String = date.toDateBrFormatWithHour()
}