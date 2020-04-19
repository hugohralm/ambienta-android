package br.com.oversight.ambienta.model

data class TipoCategoriaDenuncia(
    var id: Int? = 0,
    var nome: String = "",
    var categorias: List<CategoriaDenuncia>? = listOf()
){
    override fun toString(): String {
        return nome
    }
}