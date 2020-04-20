package br.com.oversight.ambienta.service.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.oversight.ambienta.model.Categoria
import br.com.oversight.ambienta.model.TipoCategoria

@Database(entities = [TipoCategoria::class], version = 1)
@TypeConverters(DataConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun tipoCategoriaDao(): TipoCategoriaDAO
}