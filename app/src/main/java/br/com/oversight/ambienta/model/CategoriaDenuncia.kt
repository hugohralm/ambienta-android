package br.com.oversight.ambienta.model

data class CategoriaDenuncia(
    var id: Int? = null,
    var ativo: Boolean? = null,
    var dataCadastro: String? = "",
    var nome: String = "",
    var orgao: Orgao? = Orgao()
//    var tipo: Tipo? = Tipo()
){
    override fun toString(): String {
        return nome
    }
}