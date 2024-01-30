package cn.zzwtsy.fc2service.enums

/**
 * 状态码和状态信息
 *
 * @param code 状态码
 * @param msg 消息
 * */
enum class RCode(val code: Int, val msg: String) {
    // 状态码为200，消息为"请求成功"
    SUCCESS(200, "请求成功"),

    // 状态码为500，消息为"服务器内部错误"
    ERROR(500, "服务器内部错误"),

    // 状态码为401，消息为"未授权"
    UNAUTHORIZED(401, "未授权"),

    // 状态码为404，消息为"未找到资源"
    NOT_FOUND(404, "未找到资源"),

    // 状态码为400，消息为"请求参数错误"
    PARAM_ERROR(400, "请求参数错误"),

    // 状态码为403，消息为"禁止访问"
    FORBIDDEN(403, "禁止访问"),

    // 状态码为204，消息为"暂无数据"
    NO_DATA(204, "暂无数据"),
}
