package com.dmc.model

import javax.persistence.Transient

@NoArg
data class Role(

        var id: Long,
        var pid: Long,
        var pname: String,
        var name: String,
        var remark: String,
        var seq: Int,

        @Transient
        var resourceIds: List<Long>,
        @Transient
        var resourceNames: List<String>

)