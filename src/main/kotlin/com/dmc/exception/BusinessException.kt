package com.dmc.exception

/**
 * @author yangfan
 * *
 * @date 2017/08/11
 */
class BusinessException(msg: String) : RuntimeException(msg) {
    var code: Int = 500

    constructor(code: Int, msg: String) : this(msg) {
        this.code = code
    }
}
