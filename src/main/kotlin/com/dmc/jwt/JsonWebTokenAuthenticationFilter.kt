/*
 * Copyright (c) 2016 xiaomaihd and/or its affiliates.All Rights Reserved.
 *            http://www.xiaomaihd.com
 */
package com.dmc.jwt

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter
import org.springframework.stereotype.Component

/**
 * Created by YangFan on 2016/11/28 上午10:06.
 *
 *
 * JTW验证
 */
@Component
class JsonWebTokenAuthenticationFilter : RequestHeaderAuthenticationFilter() {
    init {
        // Don't throw exceptions if the header is missing
        this.setExceptionIfHeaderMissing(false)

        // This is the request header it will look for
        this.setPrincipalRequestHeader("Authorization")
    }

    @Autowired
    override fun setAuthenticationManager(
            authenticationManager: AuthenticationManager) {
        super.setAuthenticationManager(authenticationManager)
    }
}