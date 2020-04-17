package br.com.oversight.ambienta.utils

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.BindingAdapter
import coil.api.load
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.redmadrobot.inputmask.helper.AffinityCalculationStrategy


@BindingAdapter(value = ["mascara", "mascaraSecundaria"], requireAll = false)
fun applyMask(view: EditText, mascara: String, mascaraSecundaria: String?) {
    Log.d("ATENTION!!!", "APPLY CALLED!!!!!!!!!!!!")
    if (mascaraSecundaria != null) {
        MaskedTextChangedListener.installOn(
            view,
            mascara,
            listOf(mascaraSecundaria),
            AffinityCalculationStrategy.CAPACITY
        )

    } else MaskedTextChangedListener.installOn(view, mascara)
}

@BindingAdapter(value = ["loadImage", "placeHolder", "crossFade"], requireAll = false)
fun loadImage(imageView: ImageView, url: String?, placeHolder: Drawable?, crossFade: Boolean? = true) {
    imageView.load(url){
        crossfade(crossFade!!)
        placeholder(placeHolder)
    }
}

@BindingAdapter("htmlText")
fun htmlText(view: TextView, text: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) view.text =
        (Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT))
    else view.text = (Html.fromHtml(text))
}

@BindingAdapter("imageFromRes")
fun imageFromRes(view: ImageView,@DrawableRes id: Int) {
    view.setImageResource(id)
}

@BindingAdapter(value = ["obsTextColor", "defaultTextColor"], requireAll = false)
fun setTextColor(view: TextView, textColor: String?, defaultColor: Int) {
    if (textColor != null) view.setTextColor(Color.parseColor(textColor))
    else view.setTextColor(defaultColor)
}

@BindingAdapter("showView")
fun showView(view: View, show: Boolean) {
    view.visibility = if (show) View.VISIBLE else View.GONE
}

@BindingAdapter("showViewIfFalseInvisible")
fun showViewIfFalseInvisible(view: View, show: Boolean) {
    view.visibility = if (show) View.VISIBLE else View.INVISIBLE
}

@BindingAdapter("showProgress")
fun showProgress(view: ContentLoadingProgressBar, show: Boolean) {
    if (show) view.show() else view.hide()
}

@BindingAdapter("hideView")
fun hideView(view: View, hide: Boolean) {
    view.visibility = if (!hide) View.VISIBLE else View.GONE
}

@BindingAdapter("check")
fun check(view: RadioButton, check: Boolean) {
    view.isChecked = check
}