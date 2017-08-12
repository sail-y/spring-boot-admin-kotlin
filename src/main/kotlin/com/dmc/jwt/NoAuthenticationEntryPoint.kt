/*
 * Copyright (c) 2016 xiaomaihd and/or its affiliates.All Rights Reserved.
 *            http://www.xiaomaihd.com
 */
package com.dmc.jwt

import com.dmc.model.RestResp
import com.dmc.util.JsonUtil
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by YangFan on 2016/11/28 下午1:31.
 *
 *
 */
class NoAuthenticationEntryPoint : AuthenticationEntryPoint {

    //当访问的资源没有权限，会调用这里
    @Throws(IOException::class, ServletException::class)
    override fun commence(request: HttpServletRequest, response: HttpServletResponse, authException: AuthenticationException) {


        //返回json形式的错误信息
        response.characterEncoding = "UTF-8"
        response.contentType = "application/json"

        val restResp = RestResp.error(RestResp.NO_SESSION, "没有登录或登录已过期!")

        response.writer.println(JsonUtil.toJsonString(restResp))
        response.writer.flush()
    }
}