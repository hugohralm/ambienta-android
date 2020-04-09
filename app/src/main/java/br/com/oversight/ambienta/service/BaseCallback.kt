package br.com.oversight.ambienta.service

import br.com.oversight.ambienta.R
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class BaseCallback<T> : Callback<T> {

    abstract fun onSuccess(response: T?)

    abstract fun onError(error: String)

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            onSuccess(response.body())
        } else {
            if(response.code() != 502){
                val jObjError = JSONObject(response.errorBody()?.string())
                onError(jObjError.getString("message"))
            }else{
                onError(ApplicationController.resourses.getString(R.string.msg_unavailable_service))
            }
        }
    }
}