package br.com.oversight.ambienta.ui.dialogSearch

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.DialogFragment
import br.com.oversight.ambienta.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText


class BottomSheetSearch(val listener: BottomSheetSearchActions): BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_search, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetStyle)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.findViewById<TextInputEditText>(R.id.input_cod)?.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH
                || actionId == EditorInfo.IME_ACTION_DONE
                || event.action == KeyEvent.ACTION_DOWN
                && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                dialog?.findViewById<MaterialButton>(R.id.btn_pesquisar)?.callOnClick()
                return@setOnEditorActionListener true;
            }
            return@setOnEditorActionListener false;
         }
        dialog?.findViewById<MaterialButton>(R.id.btn_pesquisar)?.setOnClickListener {
            val codValue = dialog?.findViewById<TextInputEditText>(R.id.input_cod)?.text.toString()
            if (codValue.isNotBlank()){
                listener.salvar(codValue)
            }
            dialog!!.dismiss()
        }
    }

    interface BottomSheetSearchActions {
        fun salvar(codigo: String)
    }
}