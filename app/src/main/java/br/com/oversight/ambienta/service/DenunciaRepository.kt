package br.com.oversight.ambienta.service

import androidx.lifecycle.MutableLiveData
import br.com.oversight.ambienta.model.Denuncia
import javax.inject.Inject

class DenunciaRepository @Inject constructor(private val denunciaService: DenunciaService) {
    fun getById(id: Int) : MutableLiveData<ApiResult<Denuncia>> {
        return denunciaService.getById(id);
    }
}