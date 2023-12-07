package cn.zzwtsy.fc2service.config

import org.ktorm.database.Database
import org.ktorm.jackson.KtormModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class KtormConfiguration {
    @Autowired
    lateinit var dataSource: DataSource

    /**
     * Register the [Database] instance as a Spring bean.
     */
    @Bean
    fun database(): Database {
        return Database.connectWithSpringSupport(dataSource)
    }

    /**
     * 将 Ktorm 的 Jackson 扩展注册到 Spring 的容器
     * 这样我们就可以 序列化/反序列化 Ktorm 实体。
     */
    @Bean
    fun ktormModule(): KtormModule {
        return KtormModule()
    }
}