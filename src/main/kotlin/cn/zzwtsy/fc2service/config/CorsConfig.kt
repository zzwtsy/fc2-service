package cn.zzwtsy.fc2service.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfig : WebMvcConfigurer {
    /**
     * 添加CORS映射到注册表。
     *
     * @param registry 注册表实例。
     */
    override fun addCorsMappings(registry: CorsRegistry) {
        // 添加一个CORS映射，匹配所有路径
        registry.addMapping("/**")
            // 允许所有原始请求
            .allowedOrigins("*")
            // 允许的HTTP方法包括：GET、POST、PUT、DELETE和OPTIONS
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            // 允许的请求头包括所有
            .allowedHeaders("*")
            // 允许携带凭据（如cookies）
            .allowCredentials(false)
            // 超时时间（秒）
            .maxAge(3600)
    }
}