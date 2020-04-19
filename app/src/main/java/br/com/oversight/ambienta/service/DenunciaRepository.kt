package br.com.oversight.ambienta.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.oversight.ambienta.model.CategoriaDenuncia
import br.com.oversight.ambienta.model.Denuncia
import br.com.oversight.ambienta.model.TipoCategoriaDenuncia
import br.com.oversight.ambienta.service.ApiResult
import br.com.oversight.ambienta.service.DenunciaService
import javax.inject.Inject

class DenunciaRepository @Inject constructor(private val denunciaService: DenunciaService) {
    fun create(denuncia: Denuncia): LiveData<ApiResult<Denuncia>> {
        return denunciaService.create(denuncia)
    }

    fun listarTiposCategorias(): LiveData<ApiResult<List<TipoCategoriaDenuncia>>> {
        return denunciaService.getTipoCategoriaDenuncia()
    }

    fun listarDenuncias(): LiveData<ApiResult<List<Denuncia>>> {
        return denunciaService.getAll();
    }
}
