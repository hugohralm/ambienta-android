package br.com.oversight.ambienta.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.oversight.ambienta.model.DenunciaResponse
import br.com.oversight.ambienta.service.BaseResponse
import br.com.oversight.ambienta.service.DenunciaRepository
import com.google.gson.Gson
import com.google.gson.JsonObject

class HomeViewModel : ViewModel() {
    private lateinit var denuncia: MutableLiveData<JsonObject>
    private lateinit var protocol: HomeView

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun getById(id: String): LiveData<JsonObject> {
        denuncia = MutableLiveData()
        DenunciaRepository().getById(id, object : BaseResponse<JsonObject> {
            override fun onResponseSuccess(response: JsonObject?) {
                denuncia.value = response
            }

            override fun onResponseErrorNotFound() {
                //TODO
            }

            override fun onResponseError(message: String) {
                protocol.responseError(message)
            }
        })

        return denuncia
    }

    fun setProtocol(protocol: HomeView){
        this.protocol = protocol
    }
}