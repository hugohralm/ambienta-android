package br.com.oversight.ambienta.model

import java.util.*

data class RespostaDenuncia(
    var id: Int? = null,
    var descricao: String? = null,
    var status: StatusDenuncia? = null,
    var dataCadastro: Date? = null
) {
}