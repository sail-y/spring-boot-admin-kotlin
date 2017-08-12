/*
 * Copyright (c) 2016 xiaomaihd and/or its affiliates.All Rights Reserved.
 *            http://www.xiaomaihd.com
 */
package com.dmc.jwt

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

/**
 * Created by YangFan on 2016/11/28 上午10:27.
 *
 *
 */
abstract class JsonWebTokenSecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    private val authenticationProvider: JsonWebTokenAuthenticationProvider? = null

    @Autowired
    private val jsonWebTokenFilter: JsonWebTokenAuthenticationFilter? = null

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        // This method is here with the @Bean annotation so that Spring can
        // autowire it
        return super.authenticationManagerBean()
    }

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.authenticationProvider(authenticationProvider)
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.headers()
                .frameOptions()
                .sameOrigin()
                .and()
                // disable CSRF, http basic, form login
                .csrf().disable() //
                .httpBasic().disable() //
                .formLogin().disable()

                // ReST is stateless, no sessions
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //

                .and()

                // return 403 when not authenticated
                .exceptionHandling().authenticationEntryPoint(NoAuthenticationEntryPoint())

        // Let child classes set up authorization paths
        setupAuthorization(http)

        http.addFilterBefore(jsonWebTokenFilter!!, UsernamePasswordAuthenticationFilter::class.java)
    }

    @Throws(Exception::class)
    protected abstract fun setupAuthorization(http: HttpSecurity)
}