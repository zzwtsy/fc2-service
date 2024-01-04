package cn.zzwtsy.fc2service.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies

object JsonUtil {
    private val objectMapper = ObjectMapper().apply {
        this.findAndRegisterModules()
        this.propertyNamingStrategy = PropertyNamingStrategies.LOWER_CAMEL_CASE
    }

    fun getObjectMapper(): ObjectMapper {
        return objectMapper
    }

    inline fun <reified T> fromJson(json: String): T {
        return this.getObjectMapper().readValue(json, T::class.java)
    }

    inline fun <reified T> toJson(): String {
        return getObjectMapper().writeValueAsString(T::class.java)
    }

    inline fun <reified T> toPrettyJson(): String {
        return getObjectMapper()
            .writerWithDefaultPrettyPrinter()
            .writeValueAsString(T::class.java)
    }
}