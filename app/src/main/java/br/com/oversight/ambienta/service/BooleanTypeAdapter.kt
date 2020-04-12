package br.com.oversight.ambienta.service

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type


class BooleanTypeAdapter : JsonDeserializer<Boolean> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement, typeOfT: Type,
        context: JsonDeserializationContext
    ): Boolean? {
        if (json.asBoolean) {
            return json.asBoolean
        }
        try {
            val code = json.asInt
            return if (code == 0)
                false
            else if (code == 1)
                true
            else
                null
        } catch (e: Exception) {
        }

        try {
            val code = json.asString
            return if (code.equals("false", ignoreCase = true))
                false
            else if (code.equals("true", ignoreCase = true))
                true
            else
                null
        } catch (e: Exception) {
        }

        return null

    }
}