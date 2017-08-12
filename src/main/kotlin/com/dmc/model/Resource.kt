package com.dmc.model

@NoArg
data class Resource(

        var id: Long,
        var type: String,
        var pid: Long?,
        var pname: String,
        var method: String,
        var name: String,
        var remark: String,
        var seq: Int,
        var url: String,
        var roleId: String,
        var roleName: String
)
