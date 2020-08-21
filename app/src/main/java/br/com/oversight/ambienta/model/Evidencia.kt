package br.com.oversight.ambienta.model

data class Evidencia(
    var assinatura: String? = "",
    var formato: String? = "",
    var id: Int? = 0,
    var tipoArquivo: String? = "",
    var url: String? = ""
)