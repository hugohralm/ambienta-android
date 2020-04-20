package br.com.oversight.ambienta.model

import androidx.room.*
import br.com.oversight.ambienta.service.room.DataConverter

@Entity
data class TipoCategoria(
    @PrimaryKey var id: Int? = null,
    var nome: String = "",
    var categorias: List<Categoria>? = listOf()
) {
    override fun toString(): String {
        return nome
    }
}