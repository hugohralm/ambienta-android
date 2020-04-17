package br.com.oversight.ambienta.model

data class CategoriaDenuncia(
    var id: Int? = null,
    var nome: String = ""
){
    override fun toString(): String {
        return nome
    }
}