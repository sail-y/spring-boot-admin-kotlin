package com.dmc.model

import java.util.*
import javax.persistence.Column
import javax.persistence.Transient


@NoArg
data class User(

        var id: Long,
        var username: String,
        var name: String,
        var password: String,
        var createTime: Date,
        @Column(name = "merchant_id")
        var merchantId: Long,
        var modifyTime: Date,
        @Transient
        var roleIds: List<Long>,
        @Transient
        var roleNames: List<String>,

        var oldPassword: String

)
