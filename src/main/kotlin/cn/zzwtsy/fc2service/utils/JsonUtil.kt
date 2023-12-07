package cn.zzwtsy.fc2service.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies

object JsonUtil {
    private val objectMapper = ObjectMapper().apply {
        this.findAndRegisterModules()
        this.propertyNamingStrategy = PropertyNamingStrategies.LOWER_CAMEL_CASE
    }

    fun <T> fromJson(json: String, clazz: Class<T>): T {
        return objectMapper.readValue(json, clazz)
    }

    fun <T> toJson(obj: T): String {
        return objectMapper.writeValueAsString(obj)
    }

    fun <T> toPrettyJson(obj: T): String {
        return objectMapper
            .writerWithDefaultPrettyPrinter()
            .writeValueAsString(obj)
    }
}