package br.com.oversight.ambienta.service

import androidx.lifecycle.LiveData
import br.com.oversight.ambienta.model.Denuncia
import br.com.oversight.ambienta.model.TipoCategoria
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface DenunciaService {

    @GET("api/denuncias")
    fun getAll(): LiveData<ApiResult<List<Denuncia>>>

    @POST("api/denuncias")
    fun create(@Body denuncia: Denuncia): LiveData<ApiResult<Denuncia>>

    @GET("api/tipos-categoria")
    fun getTipoCategoriaDenuncia(): LiveData<ApiResult<List<TipoCategoria>>>

}