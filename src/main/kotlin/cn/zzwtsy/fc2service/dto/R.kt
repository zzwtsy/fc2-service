package cn.zzwtsy.fc2service.dto

import cn.zzwtsy.fc2service.enums.RCode
import com.fasterxml.jackson.annotation.JsonInclude

data class R<T>(
    val code: Int,
    val message: String,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val totalPages: Int? = null,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val data: T? = null
) {
    companion object {
        fun <T> success(data: T, totalPages: Int? = null): R<T> {
            return R(RCode.SUCCESS.code, RCode.SUCCESS.msg, totalPages, data)
        }

        fun <T> failure(codeType: RCode): R<T> {
            return R(codeType.code, codeType.msg)
        }
    }
}
