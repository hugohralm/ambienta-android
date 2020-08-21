package br.com.oversight.ambienta.ui.novaDenuncia

import androidx.lifecycle.*
import br.com.oversight.ambienta.model.Denuncia
import br.com.oversight.ambienta.model.Evidencia
import br.com.oversight.ambienta.model.TipoCategoria
import br.com.oversight.ambienta.service.ApiResult
import br.com.oversight.ambienta.service.DenunciaRepository
import br.com.oversight.ambienta.service.room.AppDatabase
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import javax.inject.Inject

class NovaDenunciaViewModel @Inject constructor(
    private val denunciaRepository: DenunciaRepository,
    private val database: AppDatabase
) :
    ViewModel() {
    val isDenunciaAnonima: MutableLiveData<Boolean> = MutableLiveData<Boolean>().also {
        it.value = false
    }

    val denuncia: MutableLiveData<Denuncia> = MutableLiveData<Denuncia>().also {
        it.value = Denuncia()
    }

    val tipoCategoria: MutableLiveData<List<TipoCategoria>> =
        MutableLiveData<List<TipoCategoria>>().also {
            database.tipoCategoriaDao().getAllTipoCategoria().observeForever { categoriaList ->
                it.value = categoriaList
            }
        }

    val requestDenuncia: MutableLiveData<ApiResult<Denuncia>> =
        MutableLiveData<ApiResult<Denuncia>>().also {
            it.value = ApiResult.idle()
        }

    val count: MutableLiveData<Int> = MutableLiveData<Int>().also {
        it.value = 0
    }

    var total: Int = -1

    val isLoading = MutableLiveData<Boolean>()

    fun postDenuncia(denuncia: Denuncia) {
        denunciaRepository.create(denuncia).observeForever {
            requestDenuncia.value = it
        }
    }

    fun postEvidencias(id: RequestBody, images: MultipartBody.Part) {
        denunciaRepository.postImage(id, images).observeForever {

            if (it.status == ApiResult.Status.STATUS_SUCCESS
                || it.status == ApiResult.Status.STATUS_ERROR
            ) {
                count.value = count.value?.plus(1)
                isLoading.value = count.value != total
            }
        }
    }
}
