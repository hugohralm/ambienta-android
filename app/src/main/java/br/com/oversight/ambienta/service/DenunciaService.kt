package br.com.oversight.ambienta.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.oversight.ambienta.model.CategoriaDenuncia
import br.com.oversight.ambienta.model.Denuncia
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DenunciaService {

    @GET("api/denuncias")
    fun getAll(): LiveData<ApiResult<List<Denuncia>>>

    @POST("api/denuncias")
    fun create(@Body denuncia: Denuncia): LiveData<ApiResult<Denuncia>>

    @GET("api/categorias")
    fun getCategoriasDenuncia(): LiveData<ApiResult<List<CategoriaDenuncia>>>

}