package com.dmc.conf

import com.dmc.jwt.JsonWebTokenSecurityConfig
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

/**
 *
 * @author yangfan
 * @date 2017/08/10
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig: JsonWebTokenSecurityConfig() {
    override fun setupAuthorization(http: HttpSecurity) {
        http.authorizeRequests()

                // allow anonymous access to /user/login endpoint
                .antMatchers("/user/login").permitAll()
                .antMatchers("/swagger/**").permitAll()
                .antMatchers("/web/**").permitAll()
                .antMatchers("/").permitAll()


                // authenticate all other requests
                .anyRequest().authenticated()
    }

}