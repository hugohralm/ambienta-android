package br.com.oversight.ambienta.service

import androidx.lifecycle.LiveData
import br.com.oversight.ambienta.model.Denuncia
import br.com.oversight.ambienta.model.TipoCategoria
import javax.inject.Inject

class DenunciaRepository @Inject constructor(private val denunciaService: DenunciaService) {
    fun create(denuncia: Denuncia): LiveData<ApiResult<Denuncia>> {
        return denunciaService.create(denuncia)
    }

    fun listarTiposCategorias(): LiveData<ApiResult<List<TipoCategoria>>> {
        return denunciaService.getTipoCategoriaDenuncia()
    }

    fun listarDenuncias(): LiveData<ApiResult<List<Denuncia>>> {
        return denunciaService.getAll();
    }
}
