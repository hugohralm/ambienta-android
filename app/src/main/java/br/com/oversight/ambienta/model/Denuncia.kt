package br.com.oversight.ambienta.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import br.com.oversight.ambienta.utils.extensions.clearSpecialCharacters
import java.io.Serializable
import java.util.*

@Entity
data class Denuncia(
    @PrimaryKey var id: Int? = null,
    var codigoAcompanhamento: String? = null,
    @Ignore
    var status: StatusDenuncia? = null,

    @Ignore
    var cpfDenunciante: String? = null,
    @Ignore
    var nomeDenunciante: String? = null,
    @Ignore
    var emailDenunciante: String? = null,
    @Ignore
    var nomeDenunciado: String? = null,

    var titulo: String? = null,
    @Ignore
    var categoria: Categoria? = null,
    @Ignore
    var dataOcorrido: Date? = null,
    @Ignore
    var descricao: String? = "",
    @Ignore
    var latitude: Double? = null,
    @Ignore
    var longitude: Double? = null,
    @Ignore
    var evidencias: List<Evidencia> = mutableListOf(),
    var respostas: List<RespostaDenuncia> = mutableListOf()
): Serializable{

    fun clearMask(): Denuncia {
        val denuncia = this.copy()
        denuncia.cpfDenunciante = cpfDenunciante?.clearSpecialCharacters()
        return denuncia
    }
}