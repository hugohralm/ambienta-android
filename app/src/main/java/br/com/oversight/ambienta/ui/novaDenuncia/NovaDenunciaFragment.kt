package br.com.oversight.ambienta.ui.novaDenuncia

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.lifecycle.Observer
import br.com.oversight.ambienta.R
import br.com.oversight.ambienta.adapter.NoFilteringArrayAdapter
import br.com.oversight.ambienta.databinding.FragmentNovaDenunciaBinding
import br.com.oversight.ambienta.di.BaseFragment
import br.com.oversight.ambienta.di.RequiresViewModel
import br.com.oversight.ambienta.model.CategoriaDenuncia
import br.com.oversight.ambienta.service.ApiResult
import br.com.oversight.ambienta.utils.extensions.log
import br.com.oversight.ambienta.utils.extensions.showSnack
import br.com.oversight.ambienta.utils.extensions.toDateBrFormat
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

@RequiresViewModel(NovaDenunciaViewModel::class)
class NovaDenunciaFragment : BaseFragment<NovaDenunciaViewModel>() {

    lateinit var binding: FragmentNovaDenunciaBinding
    lateinit var datePicker: MaterialDatePicker<Long>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNovaDenunciaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Data do ocorrido").build()
        this.datePicker.addOnPositiveButtonClickListener {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = it
            calendar.add(Calendar.DAY_OF_YEAR, 1)
            viewModel!!.denuncia.value?.dataOcorrido = calendar.time
            binding.dataOcorrido.setText(calendar.time.toDateBrFormat())
        }

        this.binding.dataOcorrido.setOnClickListener {
            this.datePicker.show(childFragmentManager, datePicker.tag)
        }

        binding.btnSend.setOnClickListener {
            viewModel?.denuncia?.value?.let { log(it.toString()) }
        }
    }


    override fun bindViewModel(viewModel: NovaDenunciaViewModel) {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
        }

        viewModel.categoriaDenunciaList.observe(this, Observer {
            when(it.status){
                ApiResult.Status.STATUS_SUCCESS -> prepareAutoComplete(it.data!!)
                ApiResult.Status.STATUS_ERROR -> showSnack(binding.root, "Erro ao carregar categorias\n${it.errorMessage}")
            }
        })

    }

    private fun prepareAutoComplete(categoriaDenunciaList: List<CategoriaDenuncia> ) {

        //Gender Spinner
        binding.autocompleteCategoria.setAdapter(
            NoFilteringArrayAdapter<CategoriaDenuncia>(
                context,
                R.layout.dropdown_menu_popup_item,
                categoriaDenunciaList
            )
        )

        binding.autocompleteCategoria.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                viewModel?.denuncia?.value?.categoriaDenuncia = parent.adapter.getItem(position) as CategoriaDenuncia
                binding.autocompleteCategoria.error = null
            }
        binding.autocompleteCategoria.onFocusChangeListener = OnFocusChangeListener { _, hasFocus -> if (hasFocus && !binding.autocompleteCategoria.isPopupShowing) binding.autocompleteCategoria.showDropDown() }
        binding.autocompleteCategoria.setOnClickListener { if (!binding.autocompleteCategoria.isPopupShowing) binding.autocompleteCategoria.showDropDown() }

    }
}
