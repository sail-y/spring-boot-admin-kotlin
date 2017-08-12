package com.dmc.model


@NoArg
data class Menu(
        var id: Long? = null,
        var text: String? = null,
        var url: String? = null,
        var children: List<Menu>? = null
){

}
