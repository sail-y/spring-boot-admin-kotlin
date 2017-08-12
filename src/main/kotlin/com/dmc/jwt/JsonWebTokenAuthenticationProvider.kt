/*
 * Copyright (c) 2016 xiaomaihd and/or its affiliates.All Rights Reserved.
 *            http://www.xiaomaihd.com
 */
package com.dmc.jwt

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import org.springframework.stereotype.Component

/**
 * Created by YangFan on 2016/11/28 上午10:19.
 *
 *
 */
@Component
class JsonWebTokenAuthenticationProvider : AuthenticationProvider {

    private val tokenService = JsonWebTokenUtility()

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication? {
        var authenticatedUser: Authentication? = null
        // Only process the PreAuthenticatedAuthenticationToken
        if (authentication.javaClass.isAssignableFrom(PreAuthenticatedAuthenticationToken::class.java) && authentication.principal != null) {
            val tokenHeader = authentication.principal as String
            val userDetails: UserDetails? = parseToken(tokenHeader)
            if (userDetails != null) {
                authenticatedUser = JsonWebTokenAuthentication(userDetails, tokenHeader)
            }
        } else {
            // It is already a JsonWebTokenAuthentication
            authenticatedUser = authentication
        }
        return authenticatedUser
    }

    private fun parseToken(tokenHeader: String): UserDetails? {

        val authTokenDetails = tokenService.parseAndValidate(tokenHeader)

        val authorities = authTokenDetails?.roleNames?.map { it -> SimpleGrantedAuthority(it) }
        // userId介入Spring Security
        if (authTokenDetails != null) {
            return User(authTokenDetails.id.toString(), "",
                    authorities)
        }
        return null
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication.isAssignableFrom(
                PreAuthenticatedAuthenticationToken::class.java) || authentication.isAssignableFrom(
                JsonWebTokenAuthentication::class.java)
    }

}
