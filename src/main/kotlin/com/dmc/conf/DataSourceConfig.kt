package com.dmc.conf

import com.alibaba.druid.pool.DruidDataSource
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.annotation.PreDestroy
import javax.inject.Inject
import javax.sql.DataSource

/**
 *
 * @author yangfan
 * @date 2017/08/10
 */
@Configuration
@EnableConfigurationProperties(DruidDataSourceProperties::class)
@ConditionalOnClass(DruidDataSourceProperties::class)
class DataSourceConfig(@Inject private val properties: DruidDataSourceProperties) {

    private lateinit var dataSource: DruidDataSource

    @Bean(destroyMethod = "close")
    fun dataSource(): DataSource {
        this.dataSource = DruidDataSource()
        this.dataSource.driverClassName = properties.driverClassName
        this.dataSource.url = properties.url
        this.dataSource.username = properties.username
        this.dataSource.password = properties.password
        this.dataSource.initialSize = properties.initialSize
        this.dataSource.maxActive = properties.maxActive
        this.dataSource.isTestWhileIdle = true
        this.dataSource.minIdle = properties.minIdle
        this.dataSource.isTestOnBorrow = properties.testOnBorrow
        this.dataSource.isTestOnReturn = properties.testOnReturn
        this.dataSource.validationQuery = properties.validationQuery
        return this.dataSource
    }

    @PreDestroy
    fun close() {
        this.dataSource.close()
    }
}