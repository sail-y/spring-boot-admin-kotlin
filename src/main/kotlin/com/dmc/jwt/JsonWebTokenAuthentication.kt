/*
 * Copyright (c) 2016 xiaomaihd and/or its affiliates.All Rights Reserved.
 *            http://www.xiaomaihd.com
 */
package com.dmc.jwt

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails

/**
 * Created by YangFan on 2016/11/28 上午10:26.
 *
 *
 */
class JsonWebTokenAuthentication(private val principal: UserDetails, val jsonWebToken: String) : AbstractAuthenticationToken(principal.authorities) {

    override fun getCredentials(): Any? {
        return null
    }

    override fun getPrincipal(): Any {
        return principal
    }

    companion object {
        private val serialVersionUID = -6855809445272533821L
    }
}