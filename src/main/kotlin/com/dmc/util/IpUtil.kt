package com.dmc.util


import javax.servlet.http.HttpServletRequest

/**
 * IP工具类


 */
object IpUtil {

    /**
     * 获取登录用户的IP地址

     * @param request
     * *
     * @return
     */
    fun getIpAddr(request: HttpServletRequest): String {
        var ip: String? = request.getHeader("x-forwarded-for")
        if (ip == null || ip.length == 0 || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("Proxy-Client-IP")
        }
        if (ip == null || ip.length == 0 || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("WL-Proxy-Client-IP")
        }
        if (ip == null || ip.length == 0 || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.remoteAddr
        }
        if (ip == "0:0:0:0:0:0:0:1") {
            ip = "本地"
        }
        if (ip!!.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().size > 1) {
            ip = ip.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        }
        return ip
    }


}
