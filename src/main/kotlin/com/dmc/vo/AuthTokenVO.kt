/*
 * Copyright (c) 2016 xiaomaihd and/or its affiliates.All Rights Reserved.
 *            http://www.xiaomaihd.com
 */
package com.dmc.vo

/**
 * Created by YangFan on 2016/11/28 上午10:53.
 *
 *
 */

data class AuthTokenVO(
        val token: String? = null,
        val userId: Long? = null,
        val resourceList: List<String>? = null,
        val name: String? = null

)
