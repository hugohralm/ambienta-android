package br.com.oversight.ambienta.service


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.json.JSONObject
import retrofit2.*
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

class LiveDataCallAdapter<R : Any>(
    private val responseType: Type,
    private val retrofit: Retrofit
) :
    CallAdapter<R, LiveData<ApiResult<R>>> {

    override fun adapt(call: Call<R>): LiveData<ApiResult<R>> {
        return object : MutableLiveData<ApiResult<R>>() {
            private var started = AtomicBoolean(false)

            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {

                    postValue(ApiResult.Loading)

                    call.enqueue(object : Callback<R> {
                        override fun onResponse(call: Call<R>, response: Response<R>) {
                            if (call.isCanceled) return

                            if (response.isSuccessful) {
                                postValue(ApiResult.Success(response.body() as R))
                            } else {
                                if(response.code() != 502){
                                    val jObjError = JSONObject(response.errorBody()?.string())
                                    postValue(ApiResult.Error(jObjError.getString("message")))
                                }else{
                                    postValue(ApiResult.Error("Serviço indisponível.\\nPor favor, volte mais tarde."))
                                }
                            }
                        }

                        override fun onFailure(call: Call<R>, t: Throwable) {
                            if (call.isCanceled) return
                            postValue(ApiResult.Error(t.message?: "Erro desconhecido"))
                        }
                    })
                }
            }

        }
    }

    override fun responseType(): Type = responseType
}