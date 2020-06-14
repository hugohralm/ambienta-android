package br.com.oversight.ambienta.service

import androidx.lifecycle.LiveData
import br.com.oversight.ambienta.model.Denuncia
import br.com.oversight.ambienta.model.Evidencia
import br.com.oversight.ambienta.model.TipoCategoria
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import javax.inject.Inject

class DenunciaRepository @Inject constructor(private val denunciaService: DenunciaService) {
    fun create(denuncia: Denuncia): LiveData<ApiResult<Denuncia>> {
        return denunciaService.create(denuncia)
    }

    fun listarTiposCategorias(): LiveData<ApiResult<List<TipoCategoria>>> {
        return denunciaService.getTipoCategoriaDenuncia()
    }

    fun listarDenuncias(codigos: List<String>): LiveData<ApiResult<List<Denuncia>>> {
        return denunciaService.getAll(codigos)
    }

    fun postImage(id: RequestBody, file: MultipartBody.Part): LiveData<ApiResult<Evidencia>> {
        return denunciaService.postImage(file, id)
    }
}
