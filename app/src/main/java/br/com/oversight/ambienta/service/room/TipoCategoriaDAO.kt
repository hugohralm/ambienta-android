package br.com.oversight.ambienta.service.room

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.oversight.ambienta.model.TipoCategoria

@Dao
interface TipoCategoriaDAO {
    //tipoCategoria
    @Transaction
    @Query("SELECT * FROM TipoCategoria")
    fun getAllTipoCategoria(): LiveData<List<TipoCategoria>>

    @Insert
    suspend fun insertTipoCategoria(tipoCategorias: List<TipoCategoria>)
}