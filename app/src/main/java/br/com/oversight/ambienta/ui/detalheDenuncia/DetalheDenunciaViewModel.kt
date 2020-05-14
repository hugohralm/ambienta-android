package br.com.oversight.ambienta.ui.detalheDenuncia

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.oversight.ambienta.model.Denuncia
import br.com.oversight.ambienta.service.ApiResult
import javax.inject.Inject

class DetalheDenunciaViewModel @Inject constructor() : ViewModel() {
    val denuncia: MutableLiveData<Denuncia> = MutableLiveData<Denuncia>()
}
