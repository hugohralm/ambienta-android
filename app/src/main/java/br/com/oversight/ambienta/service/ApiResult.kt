package br.com.oversight.ambienta.service

import br.com.oversight.ambienta.service.ApiResult.Status.*
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit


class ApiResult<T> private constructor() {
    var data: T? = null
        private set
    var errorMessage: String? = null
        private set
    var status: Status = STATUS_IDLE
        private set

    enum class Status {
        STATUS_LOADING,
        STATUS_SUCCESS,
        STATUS_ERROR,
        STATUS_IDLE
    }

    val isStatusSuccess: Boolean
        get() = status == STATUS_SUCCESS

    val isStatusLoading: Boolean
        get() = status == STATUS_LOADING

    val isStatusError: Boolean
        get() = status == STATUS_ERROR

    val isStatusIdle: Boolean
        get() = status == STATUS_IDLE

    companion object {

        fun <T> loading(): ApiResult<T> {
            val resource = ApiResult<T>()
            resource.data = null
            resource.status = STATUS_LOADING
            return resource
        }

        fun <T> success(response: T?): ApiResult<T> {
            val resource = ApiResult<T>()
            resource.data = response
            resource.status = STATUS_SUCCESS
            return resource
        }

        fun <T> apiError(erroMessage: String?): ApiResult<T> {
            val resource = ApiResult<T>()
            resource.errorMessage = erroMessage
            resource.status = STATUS_ERROR
            return resource
        }

        fun <T> empty(): ApiResult<T> {
            val resource = ApiResult<T>()
            resource.status = STATUS_SUCCESS
            return resource
        }

        fun <T> idle(): ApiResult<T> {
            val resource = ApiResult<T>()
            resource.status = STATUS_IDLE
            return resource
        }

        fun <T> idle(payload: T?): ApiResult<T> {
            val resource = ApiResult<T>()
            resource.status = STATUS_IDLE
            resource.data = payload
            return resource
        }
    }
}