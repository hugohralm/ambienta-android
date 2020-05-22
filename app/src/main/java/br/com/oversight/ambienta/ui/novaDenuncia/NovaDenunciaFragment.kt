package br.com.oversight.ambienta.ui.novaDenuncia

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import br.com.oversight.ambienta.R
import br.com.oversight.ambienta.adapter.NoFilteringArrayAdapter
import br.com.oversight.ambienta.databinding.FragmentNovaDenunciaBinding
import br.com.oversight.ambienta.di.BaseFragment
import br.com.oversight.ambienta.di.RequiresViewModel
import br.com.oversight.ambienta.model.Categoria
import br.com.oversight.ambienta.model.TipoCategoria
import br.com.oversight.ambienta.service.ApiResult
import br.com.oversight.ambienta.utils.Validations
import br.com.oversight.ambienta.utils.extensions.hideKeyboard
import br.com.oversight.ambienta.utils.extensions.showSnack
import br.com.oversight.ambienta.utils.extensions.toDateBrFormat
import br.com.oversight.ambienta.utils.extensions.toDateBrFormatWithHour
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import pl.aprilapps.easyphotopicker.MediaFile
import pl.aprilapps.easyphotopicker.MediaSource
import timber.log.Timber
import java.util.*


@RuntimePermissions
@RequiresViewModel(NovaDenunciaViewModel::class)
class NovaDenunciaFragment : BaseFragment<NovaDenunciaViewModel>(),
    EvidenciasListAdapter.EvidenciasAdapterCallbacks {
    private val args: NovaDenunciaFragmentArgs by navArgs()

    lateinit var binding: FragmentNovaDenunciaBinding
    lateinit var datePicker: MaterialDatePicker<Long>
    private val evidenciasListAdapter = EvidenciasListAdapter(this)
    private lateinit var easyImage: EasyImage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNovaDenunciaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.viewModel?.denuncia?.value?.apply {
            latitude = args.latLng.latitude
            longitude = args.latLng.longitude
        }

        easyImage = EasyImage.Builder(requireContext()).allowMultiple(true).build()

        binding.recyclerViewPhotos.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = evidenciasListAdapter

        }

        prepareCalendar()

        binding.btnSend.setOnClickListener {
            hideKeyboard()
            if (validateForm()) {
                viewModel!!.denuncia.value?.clearMask()?.let { viewModel?.postDenuncia(it) }
            }
        }
    }

    private fun prepareCalendar() {
        this.datePicker = MaterialDatePicker.Builder.datePicker()
            .setCalendarConstraints(CalendarConstraints.Builder().setValidator(object :
                CalendarConstraints.DateValidator {
                override fun writeToParcel(dest: Parcel?, flags: Int) {}
                override fun isValid(date: Long): Boolean =
                    (date < Calendar.getInstance().timeInMillis)

                override fun describeContents(): Int = 0

            }).setEnd(Calendar.getInstance().timeInMillis).build()).setTitleText("Data do ocorrido")
            .build()

        this.datePicker.addOnPositiveButtonClickListener {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            Timber.i(calendar.time.toDateBrFormatWithHour())
            calendar.timeInMillis = it
            calendar.add(Calendar.DAY_OF_YEAR, 1)
            viewModel!!.denuncia.value?.dataOcorrido = calendar.time
            binding.dataOcorrido.setText(calendar.time.toDateBrFormat())
        }

        this.binding.dataOcorrido.setOnClickListener {
            this.datePicker.show(childFragmentManager, datePicker.tag)
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true
        binding.inputCpfDenunciante.isErrorEnabled = false

        if (!binding.denunciaAnonimaCheck.isChecked) {

            binding.inputCpfDenunciante.editText?.text.toString().apply {
                binding.inputCpfDenunciante.isErrorEnabled = false
                if (isEmpty()) {
                    isValid = false
                    binding.inputCpfDenunciante.error = "Este campo é obrigatório"
                } else if (!Validations.isCPF(this)) {
                    isValid = false
                    binding.inputCpfDenunciante.error = "CPF inválido"
                }
            }

            binding.inputEmail.editText?.text.toString().apply {
                binding.inputEmail.isErrorEnabled = false
                if (isEmpty()) {
                    binding.inputEmail.error = "Este campo é obrigatório"
                    isValid = false
                } else if (!Patterns.EMAIL_ADDRESS.matcher(this).matches()) {
                    binding.inputEmail.error = "E-mail inválido"
                    isValid = false
                }
            }

            if (!requireField(listOf(binding.inputNomeDenunciante, binding.inputEmail)))
                isValid = false
        }

        if (!requireField(
                listOf(
                    binding.inputDataOcorrido,
                    binding.inputDescricao,
                    binding.inputTitulo,
                    binding.inputCategoria
                )
            )
        )
            isValid = false

        return isValid
    }

    private fun requireField(input: List<TextInputLayout>): Boolean {
        var isValid = true
        input.forEach {
            it.isErrorEnabled = false
            if (it.editText?.text.toString().isEmpty()) {
                it.error = "Este campo é obrigatório"
                isValid = false
            }
        }

        return isValid
    }

    override fun bindViewModel(viewModel: NovaDenunciaViewModel) {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
        }

        viewModel.isDenunciaAnonima.observe(this, Observer {
            binding.wrapDenunciante.visibility = if (it) View.GONE else View.VISIBLE
        })

        viewModel.tipoCategoria.observe(this, Observer {
            if (it.isNotEmpty()) prepareTipoCategoriaDenuncia(it)
        })

        viewModel.requestDenuncia.observe(this, Observer { denunciaRequest ->
            when (denunciaRequest.status) {
                ApiResult.Status.STATUS_SUCCESS -> {
                    val denuncia = denunciaRequest.data!!
                    if (evidenciasListAdapter.getEvidencias().isNotEmpty()) {
                        showSnack(binding.root, "Denuncia cadastrada com sucesso")

                        val evidencias = evidenciasListAdapter.getEvidencias()
                        viewModel.total = evidencias.size
                        viewModel.count.observe(this, Observer {
                            if (it == viewModel.total) {
                                findNavController().navigate(
                                    NovaDenunciaFragmentDirections.actionNovaDenunciaFragmentToDenunciaRegisterSuccess(
                                        denuncia.codigoAcompanhamento!!
                                    )
                                )
                            }
                        })
                        evidencias.forEach { mediaFile ->

                            val body = MultipartBody.Part.createFormData(
                                "file",
                                mediaFile.file.name,
                                mediaFile.file.asRequestBody("image/*".toMediaTypeOrNull())
                            )

                            viewModel.postEvidencias(
                                denuncia.id!!.toString()
                                    .toRequestBody("text/plain".toMediaTypeOrNull()), body
                            )
                        }


                    } else  findNavController().navigate(
                        NovaDenunciaFragmentDirections.actionNovaDenunciaFragmentToDenunciaRegisterSuccess(
                            denuncia.codigoAcompanhamento!!
                        )
                    )
                }
                ApiResult.Status.STATUS_ERROR -> showSnack(
                    binding.root,
                    "Erro: ${denunciaRequest.errorMessage}"
                )
                ApiResult.Status.STATUS_LOADING -> viewModel.isLoading.value = true
            }
        })
    }

    private fun prepareCategoriaDenuncia(categoriaList: List<Categoria>) {
        //Categoria Spinner
        binding.autocompleteCategoria.setAdapter(
            NoFilteringArrayAdapter(
                context,
                R.layout.dropdown_menu_popup_item,
                categoriaList
            )
        )

        binding.autocompleteCategoria.onItemClickListener =
            OnItemClickListener { parent, _, position, _ ->
                viewModel?.denuncia?.value?.categoria =
                    parent.adapter.getItem(position) as Categoria
                binding.autocompleteCategoria.error = null
            }
        binding.autocompleteCategoria.onFocusChangeListener =
            OnFocusChangeListener { _, hasFocus -> if (hasFocus && !binding.autocompleteCategoria.isPopupShowing) binding.autocompleteCategoria.showDropDown() }
        binding.autocompleteCategoria.setOnClickListener { if (!binding.autocompleteCategoria.isPopupShowing) binding.autocompleteCategoria.showDropDown() }
    }

    private fun prepareTipoCategoriaDenuncia(tipoCategoriaList: List<TipoCategoria>) {
        //Tipo Categoria Spinner
        binding.autocompleteTipoCategoria.setAdapter(
            NoFilteringArrayAdapter(
                context,
                R.layout.dropdown_menu_popup_item,
                tipoCategoriaList
            )
        )

        binding.autocompleteTipoCategoria.onItemClickListener =
            OnItemClickListener { parent, _, position, _ ->
                (parent.adapter.getItem(position) as TipoCategoria).categorias?.let {
                    prepareCategoriaDenuncia(it)
                    viewModel?.denuncia?.value?.categoria = null
                    binding.autocompleteCategoria.text = null
                }
                binding.autocompleteTipoCategoria.error = null
            }
        binding.autocompleteTipoCategoria.onFocusChangeListener =
            OnFocusChangeListener { _, hasFocus -> if (hasFocus && !binding.autocompleteTipoCategoria.isPopupShowing) binding.autocompleteTipoCategoria.showDropDown() }
        binding.autocompleteTipoCategoria.setOnClickListener { if (!binding.autocompleteTipoCategoria.isPopupShowing) binding.autocompleteTipoCategoria.showDropDown() }

    }

    override fun addEvidencia() {
        openChooserWithPermissionCheck()
    }

    @NeedsPermission(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    fun openChooser() {
        easyImage.openChooser(this)
    }

    private fun onPhotosReturned(imageFiles: Array<MediaFile>) {
        imageFiles.forEach {
            evidenciasListAdapter.addItem(it)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        activity?.let {
            easyImage.handleActivityResult(requestCode, resultCode, data, it,
                object : DefaultCallback() {
                    override fun onMediaFilesPicked(
                        imageFiles: Array<MediaFile>,
                        source: MediaSource
                    ) {
                        onPhotosReturned(imageFiles)
                    }

                    override fun onImagePickerError(error: Throwable, source: MediaSource) {
                        //Some error handling
                        error.printStackTrace()
                    }
                })
        }
    }
}
