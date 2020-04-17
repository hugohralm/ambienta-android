package br.com.oversight.ambienta.ui.novaDenuncia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.oversight.ambienta.model.CategoriaDenuncia
import br.com.oversight.ambienta.model.Denuncia
import br.com.oversight.ambienta.service.ApiResult
import br.com.oversight.ambienta.service.repository.DenunciaRepository
import javax.inject.Inject

class NovaDenunciaViewModel @Inject constructor(private val denunciaRepository: DenunciaRepository) :
    ViewModel() {
    val denuncia: MutableLiveData<Denuncia> = MutableLiveData<Denuncia>().also {
        it.value = Denuncia()
    }

    val categoriaDenunciaList: MutableLiveData<ApiResult<List<CategoriaDenuncia>>> =
        MutableLiveData<ApiResult<List<CategoriaDenuncia>>>().also {
            denunciaRepository.listarCategorias().observeForever { result ->
                it.value = result
            }
        }

    val requestDenuncia: MutableLiveData<ApiResult<Denuncia>> = MutableLiveData<ApiResult<Denuncia>>().also {
        it.value = ApiResult.idle()
    }

    fun postDenuncia(denuncia: Denuncia) {
        denunciaRepository.create(denuncia).observeForever{
            requestDenuncia.value = it
        }
    }
}
