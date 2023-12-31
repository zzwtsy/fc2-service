package cn.zzwtsy.fc2service.enum

enum class RCode(val code: Int, val msg: String) {
    SUCCESS(200, "请求成功"),
    ERROR(500, "服务器内部错误"),
    UNAUTHORIZED(401, "未授权"),
    NOT_FOUND(404, "未找到资源"),
    PARAM_ERROR(400, "请求参数错误"),
    FORBIDDEN(403, "禁止访问"),
    NO_DATA(204, "无数据返回"),
}