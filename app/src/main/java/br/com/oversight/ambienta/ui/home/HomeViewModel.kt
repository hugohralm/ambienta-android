package br.com.oversight.ambienta.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.oversight.ambienta.service.BaseResponse
import br.com.oversight.ambienta.service.DenunciaRepository
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

        return denuncia
    }

    fun setProtocol(protocol: HomeView){
        this.protocol = protocol
    }
}