package br.com.oversight.ambienta.utils

import com.orhanobut.hawk.Hawk

object CodDenunciaHandler {

    private const val DENUNCIA_PREFERENCES = "COD_DENUNCIAS"

    @JvmStatic
    fun getCodigos(): MutableList<String> = Hawk.get<MutableList<String>>(DENUNCIA_PREFERENCES) ?: mutableListOf()

    @JvmStatic
    fun addCodigo(codigo: String) {
        val list = getCodigos()
        list.add(codigo)
        Hawk.put(DENUNCIA_PREFERENCES, list)
    }

    @JvmStatic
    fun removeCodigo(codigo: String) {
        val list = getCodigos()
        list.remove(codigo)
        Hawk.put(DENUNCIA_PREFERENCES, list)
    }
}