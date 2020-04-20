package br.com.oversight.ambienta.ui.novaDenuncia

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.oversight.ambienta.model.Denuncia
import br.com.oversight.ambienta.model.TipoCategoria
import br.com.oversight.ambienta.service.ApiResult
import br.com.oversight.ambienta.service.DenunciaRepository
import br.com.oversight.ambienta.service.room.AppDatabase
import javax.inject.Inject

class NovaDenunciaViewModel @Inject constructor(private val denunciaRepository: DenunciaRepository, private val database: AppDatabase) :
    ViewModel() {
    val isDenunciaAnonima: MutableLiveData<Boolean> = MutableLiveData<Boolean>().also {
        it.value = false
    }

    val denuncia: MutableLiveData<Denuncia> = MutableLiveData<Denuncia>().also {
        it.value = Denuncia()
    }

    val tipoCategoria: MutableLiveData<List<TipoCategoria>> = MutableLiveData<List<TipoCategoria>>().also {
            database.tipoCategoriaDao().getAllTipoCategoria().observeForever{categoriaList ->
                it.value = categoriaList
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
