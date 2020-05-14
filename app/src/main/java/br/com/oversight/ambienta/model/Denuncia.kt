package br.com.oversight.ambienta.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.oversight.ambienta.utils.extensions.clearSpecialCharacters
import java.io.Serializable
import java.util.*

@Entity
data class Denuncia(
    @PrimaryKey var id: Int? = null,
    var codigoAcompanhamento: String? = null,
    var status: StatusDenuncia? = null,

    var cpfDenunciante: String? = null,//
    var nomeDenunciante: String? = null,//
    var emailDenunciante: String? = null,//

    var nomeDenunciado: String? = null,

    var titulo: String? = null,
    var categoria: Categoria? = null,
    var dataOcorrido: Date? = null,
    var descricao: String? = "",
    var latitude: Double? = null,
    var longitude: Double? = null,
    var evidencias: List<Evidencia> = mutableListOf()
): Serializable{

    fun clearMask(): Denuncia {
        val denuncia = this.copy()
        denuncia.cpfDenunciante = cpfDenunciante?.clearSpecialCharacters()
        return denuncia
    }
}