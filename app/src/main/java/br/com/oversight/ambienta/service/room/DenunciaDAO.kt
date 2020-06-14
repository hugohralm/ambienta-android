package br.com.oversight.ambienta.service.room

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import br.com.oversight.ambienta.model.Denuncia
import br.com.oversight.ambienta.model.TipoCategoria

@Dao
interface DenunciaDAO {

    @Query("SELECT * FROM Denuncia")
    fun getAll(): LiveData<List<Denuncia>>

    @Insert(onConflict = REPLACE)
    suspend fun insertCode(code: List<Denuncia>)

    @Insert(onConflict = REPLACE)
    suspend fun insertCode(code: Denuncia)
}