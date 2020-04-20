package br.com.oversight.ambienta.model


data class Categoria(
    var id: Int? = null,
    var nome: String = "",
    var tipoCategoriaId: Int
) {
    override fun toString(): String {
        return nome
    }
}