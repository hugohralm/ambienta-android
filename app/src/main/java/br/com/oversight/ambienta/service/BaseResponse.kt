package br.com.oversight.ambienta.service

interface BaseResponse<T> {
    fun onResponseSuccess(response: T?)
    fun onResponseError(message: String)
    fun onResponseErrorNotFound()
}