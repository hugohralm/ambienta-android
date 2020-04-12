package br.com.oversight.ambienta.service

import androidx.lifecycle.MutableLiveData
import br.com.oversight.ambienta.model.Denuncia
import retrofit2.http.GET
import retrofit2.http.Path

interface DenunciaService {

    @GET("denuncia/{id}")
    fun getById(@Path("id") id: Int): MutableLiveData<ApiResult<Denuncia>>
}