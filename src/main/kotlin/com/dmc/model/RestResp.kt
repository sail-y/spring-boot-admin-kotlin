package com.dmc.model

import java.util.*

/**
 * Error
 *
 *
 * Error

 * @author yangfan
 */

class RestResp<out T> private constructor(
        val data: T?
) {

    // 默认成功
    var code: Int = OK

    var msg: String? = null

    var timestamp: Date? = null


    private constructor(code: Int, msg: String) : this(null) {
        this.code = code
        this.msg = msg
    }


    companion object {


        val OK = 200
        val ERROR = 500
        val NO_PERMISSION = 10001
        val NO_SESSION = 10002
        val NOT_FOUND = 404


        fun ok(msg: String): RestResp<String> = RestResp(OK, msg)

        fun error(code: Int, msg: String): RestResp<String> = RestResp<String>(code, msg)

    }
}
