package com.dmc.jwt

import java.util.*

/**
 * session信息模型

 * @author yangfan
 */

data class AuthTokenDetails(

        // 用户ID
        var id: Long,
        // 用户登录名
        var username: String,
        var roleNames: List<String>,
        var expirationDate: Date
)
