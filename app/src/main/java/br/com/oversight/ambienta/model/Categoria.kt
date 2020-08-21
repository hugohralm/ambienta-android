package br.com.oversight.ambienta.model

import java.io.Serializable


data class Categoria(
    var id: Int? = null,
    var nome: String = "",
    var tipoCategoriaId: Int
): Serializable {
    override fun toString(): String {
        return nome
    }
}