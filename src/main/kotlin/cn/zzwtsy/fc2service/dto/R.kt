package cn.zzwtsy.fc2service.dto

import cn.zzwtsy.fc2service.enums.RCode
import com.fasterxml.jackson.annotation.JsonInclude

data class R<T>(
    val code: Int, // 返回码
    val message: String, // 返回消息
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val totalPages: Int? = null, // 总页数（可选）
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val data: T? = null // 数据（可选）
) {
    companion object {
        /**
         * 创建一个成功的R对象
         *
         * @param data 数据
         * @param totalPages 总页数
         * @return R对象
         */
        fun <T> success(data: T, totalPages: Int? = null): R<T> {
            return R(RCode.SUCCESS.code, RCode.SUCCESS.msg, totalPages, data)
        }

        /**
         * 创建一个失败的R对象
         *
         * @param codeType 返回码类型
         * @return R对象
         */
        fun <T> failure(codeType: RCode): R<T> {
            return R(codeType.code, codeType.msg)
        }
    }
}

