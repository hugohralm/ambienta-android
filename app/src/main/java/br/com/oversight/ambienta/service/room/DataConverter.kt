package br.com.oversight.ambienta.service.room

import androidx.room.TypeConverter
import br.com.oversight.ambienta.model.Categoria
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class DataConverter {

    @TypeConverter
    fun fromCategoriaList(categoriaList: List<Categoria?>?): String? {
        if (categoriaList == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Categoria?>?>() {}.type
        return gson.toJson(categoriaList, type)
    }

    @TypeConverter
    fun toCategoriaList(countryLangString: String?): List<Categoria>? {
        if (countryLangString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Categoria?>?>() {}.type
        return gson.fromJson<List<Categoria>>(countryLangString, type)
    }
}