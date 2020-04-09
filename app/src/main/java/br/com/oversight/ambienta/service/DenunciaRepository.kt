package br.com.oversight.ambienta.service

import br.com.oversight.ambienta.R
import com.google.gson.JsonObject
import retrofit2.Call

class DenunciaRepository {
    private val service = ApplicationController.service

    fun getById(id: String, listener: BaseResponse<JsonObject>) {
        service.getById(id).enqueue(object : BaseCallback<JsonObject>() {
            override fun onError(error: String) {
                listener.onResponseError(error)
            }

            override fun onSuccess(response: JsonObject?) {
                listener.onResponseSuccess(response)
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                listener.onResponseError(ApplicationController.resourses.getString(R.string.msg_unavailable_service))
            }
        })
    }
}