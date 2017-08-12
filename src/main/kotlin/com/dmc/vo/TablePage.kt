package com.dmc.vo

import com.dmc.model.NoArg

/**
 * Created by sail on 2016/12/25.
 */
@NoArg
open class TablePage {

    var start: Int? = null
    var length: Int? = null
    var draw: Int = 1
}
