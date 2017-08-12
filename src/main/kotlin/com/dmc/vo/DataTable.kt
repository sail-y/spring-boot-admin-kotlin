package com.dmc.vo

/**
 * Created by sail on 2016/12/25.
 */
data class DataTable<out T>(
        val draw: Int,
        val recordsTotal: Long,
        val recordsFiltered: Long,
        val data: List<T>
)