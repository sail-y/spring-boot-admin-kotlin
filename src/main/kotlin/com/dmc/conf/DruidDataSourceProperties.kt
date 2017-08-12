package com.dmc.conf

import com.dmc.model.NoArg
import org.springframework.boot.context.properties.ConfigurationProperties

/**
 *
 * @author yangfan
 * @date 2017/08/10
 */
@ConfigurationProperties(prefix = "spring.datasource")
@NoArg
data class DruidDataSourceProperties (
        /**
         * Fully qualified name of the JDBC driver. Auto-detected based on the URL by default.
         */
        var driverClassName: String,

        /**
         * JDBC url of the database.
         */
        var url: String,

        /**
         * Login user of the database.
         */
        var username: String,

        /**
         * Login password of the database.
         */
        var password: String,

        var initialSize: Int,

        var minIdle: Int,

        var maxActive: Int,

        var maxWait: Int,

        var timeBetweenEvictionRunsMillis: Int,

        var minEvictableIdleTimeMillis: Int,

        var validationQuery: String,

        var testWhileIdle: Boolean,

        var testOnBorrow: Boolean,

        var testOnReturn: Boolean,

        var poolPreparedStatements: Boolean,

        var maxPoolPreparedStatementPerConnectionSize: Int,

        var filters: String,

        var connectionProperties: String
)