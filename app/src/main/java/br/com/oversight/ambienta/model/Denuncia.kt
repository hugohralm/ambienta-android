package br.com.oversight.ambienta.model

import java.util.*

data class Denuncia(
    var id: Int? = null,
    var codigoAcompanhamento: String? = null,
    var status: StatusDenuncia? = null,
    var titulo: String? = null,

    var cpfDenunciante: String? = null,//
    var nomeDenunciante: String? = null,//
    var emailDenunciante: String? = null,//
    var telefoneDenunciante: String? = null,//

    var nomeDenunciado: String? = null,

    var categoriaDenuncia: CategoriaDenuncia? = null,
    var dataCadastro: Date? = null,
    var dataOcorrido: Date? = null,
    var descricao: String? = "",//
    var latitude: Double? = null,
    var longitude: Double? = null,
    var municipio: Municipio? = null
)