package br.com.oversight.ambienta.service

import androidx.lifecycle.LiveData
import br.com.oversight.ambienta.model.Denuncia
import br.com.oversight.ambienta.model.Evidencia
import br.com.oversight.ambienta.model.TipoCategoria
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.*

interface DenunciaService {

    @GET("api/denuncias/acompanhar")
    fun getAll(@Query("codigo") codigos: List<String>): LiveData<ApiResult<List<Denuncia>>>

    @POST("api/denuncias")
    fun create(@Body denuncia: Denuncia): LiveData<ApiResult<Denuncia>>

    @GET("api/tipos-categoria")
    fun getTipoCategoriaDenuncia(): LiveData<ApiResult<List<TipoCategoria>>>

    @Multipart
    @POST("api/evidencias")
    fun postImage(@Part file: MultipartBody.Part, @Part("denuncia_id") id: RequestBody): LiveData<ApiResult<Evidencia>>
}