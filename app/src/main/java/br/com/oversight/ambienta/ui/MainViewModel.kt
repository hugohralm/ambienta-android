package br.com.oversight.ambienta.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.oversight.ambienta.model.TipoCategoria
import br.com.oversight.ambienta.service.ApiResult
import br.com.oversight.ambienta.service.DenunciaRepository
import br.com.oversight.ambienta.service.room.AppDatabase
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val denunciaRepository: DenunciaRepository
) : ViewModel() {

    val tipoCategoria: MutableLiveData<ApiResult<List<TipoCategoria>>> =
        MutableLiveData<ApiResult<List<TipoCategoria>>>().also {
//            denunciaRepository.listarTiposCategorias().observeForever { result ->
//                it.value = result
//            }
        }

}