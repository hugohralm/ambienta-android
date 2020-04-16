package br.com.oversight.ambienta.service

import androidx.lifecycle.LiveData
import br.com.oversight.ambienta.model.TipoDenuncia
import com.google.gson.reflect.TypeToken
import retrofit2.CallAdapter
import retrofit2.CallAdapter.Factory
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class LiveDataCallAdapterFactory: Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {

        if (getRawType(returnType) != LiveData::class.java) {
            return null
        }
        if (returnType !is ParameterizedType) {
            throw IllegalStateException("Response must be parametrized as " + "LiveData<Resource> or LiveData<? extends Resource>")
        }

        val responseType = getParameterUpperBound(0, returnType)
        if (getRawType(responseType) == ApiResult::class.java) {
            if (responseType !is ParameterizedType) {
                //Timber.i("Response must be parametrized")
                throw IllegalStateException("Response must be parametrized as " + "LiveData<Response<Resource>> or LiveData<Response<? extends Resource>>")
            }
        }

        val innerType = getParameterUpperBound(0, responseType as ParameterizedType)

        return LiveDataCallAdapter<Any>(
                innerType
        )
    }
}