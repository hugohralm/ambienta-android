package br.com.oversight.ambienta.model

import br.com.oversight.ambienta.utils.extensions.clearSpecialCharacters
import java.util.*

data class Denuncia(
    var id: Int? = null,
    var codigoAcompanhamento: String? = null,
    var status: StatusDenuncia? = null,

    var cpfDenunciante: String? = null,//
    var nomeDenunciante: String? = null,//
    var emailDenunciante: String? = null,//

    var nomeDenunciado: String? = null,

    var titulo: String? = null,
    var categoria: CategoriaDenuncia? = null,
    var dataOcorrido: Date? = null,
    var descricao: String? = "",
    var latitude: Double? = null,
    var longitude: Double? = null
){

    fun clearMask(): Denuncia {
        val denuncia = this.copy()
        denuncia.cpfDenunciante = cpfDenunciante?.clearSpecialCharacters()
        return denuncia
    }
}