package br.com.oversight.ambienta.service.room

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import br.com.oversight.ambienta.model.TipoCategoria

@Dao
interface TipoCategoriaDAO {
    //tipoCategoria
    @Transaction
    @Query("SELECT * FROM TipoCategoria")
    fun getAllTipoCategoria(): LiveData<List<TipoCategoria>>

    @Insert(onConflict = REPLACE)
    suspend fun insertTipoCategoria(tipoCategorias: List<TipoCategoria>)
}