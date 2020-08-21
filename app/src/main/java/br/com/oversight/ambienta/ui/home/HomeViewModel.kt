package br.com.oversight.ambienta.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.oversight.ambienta.model.Denuncia
import br.com.oversight.ambienta.service.ApiResult
import br.com.oversight.ambienta.service.DenunciaRepository
import br.com.oversight.ambienta.utils.CodDenunciaHandler
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val denunciaRepository: DenunciaRepository
) : ViewModel() {

    val denunciaList: MutableLiveData<ApiResult<List<Denuncia>>> =
        MutableLiveData<ApiResult<List<Denuncia>>>().also {
            fetchDenuncias();
        }

    fun fetchDenuncias() {
        denunciaRepository.listarDenuncias(CodDenunciaHandler.getCodigos()).observeForever { request ->
            denunciaList.value = request
        }
    }
}