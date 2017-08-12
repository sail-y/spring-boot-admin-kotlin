/*
 * Copyright (c) 2016 xiaomaihd and/or its affiliates.All Rights Reserved.
 *            http://www.xiaomaihd.com
 */
package com.dmc.jwt

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.security.Key
import java.util.*
import javax.crypto.spec.SecretKeySpec

/**
 * Created by YangFan on 2016/11/28 上午10:01.
 *
 *
 */
class JsonWebTokenUtility {

    val log: Logger = LoggerFactory.getLogger(this::class.java)

    val signatureAlgorithm: SignatureAlgorithm = SignatureAlgorithm.HS512

    private val secretKey: Key

    init {

        // 这里不是真正安全的实践
        // 为了简单，我们存储一个静态key在这里，
        val encodedKey = "L7A/6zARSkK1j7Vd5SDD9pSSqZlqF7mAhiOgRbgv9Smce6tf4cJnvKOjtKPxNNnWQj+2lQEScm3XIUjhW+YVZg=="
        secretKey = deserializeKey(encodedKey)
    }

    fun createJsonWebToken(authTokenDetails: AuthTokenDetails): String {
        val token = Jwts.builder().setSubject(authTokenDetails.id.toString())
                .claim("username", authTokenDetails.username)
                .claim("roleNames", authTokenDetails.roleNames)
                .setExpiration(authTokenDetails.expirationDate)
                .signWith(signatureAlgorithm,
                        secretKey).compact()
        return token
    }

    private fun deserializeKey(encodedKey: String): Key {
        val decodedKey = Base64.getDecoder().decode(encodedKey)
        val key = SecretKeySpec(decodedKey, signatureAlgorithm.jcaName)
        return key
    }

    fun parseAndValidate(token: String): AuthTokenDetails? {
        var authTokenDetails: AuthTokenDetails? = null
        try {
            val claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body
            val userId = claims.subject
            val username = claims["username"] as String
            val roleNames = claims["roleNames"] as List<String>
            val expirationDate = claims.expiration

            authTokenDetails = AuthTokenDetails(userId.toLong(), username, roleNames = roleNames, expirationDate = expirationDate)
        } catch (ex: JwtException) {
            log.error(ex.message, ex)
        }

        return authTokenDetails
    }

    private fun serializeKey(key: Key): String {
        val encodedKey = Base64.getEncoder().encodeToString(key.encoded)
        return encodedKey
    }
}
