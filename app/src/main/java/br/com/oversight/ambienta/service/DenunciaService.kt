package br.com.oversight.ambienta.service

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DenunciaService {

    @GET("denuncia/{id}")
    fun getById(@Path("id") id: String): Call<JsonObject>
}