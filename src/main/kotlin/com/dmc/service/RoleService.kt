package com.dmc.service

import com.dmc.model.Role
import com.dmc.vo.DataTable
import com.dmc.vo.RoleVO

/**
 * 角色业务逻辑

 * @author yangfan
 */
interface RoleService {

    /**
     * 保存角色

     * @param role
     */
    fun add(role: Role)

    /**
     * 获得角色

     * @param id
     * *
     * @return
     */
    fun get(roleId: Long): Role

    /**
     * 编辑角色

     * @param role
     */
    fun edit(role: Role)

    /**
     * 获得角色treeGrid

     * @return
     */
    fun treeGrid(): List<Role>

    /**
     * 删除角色

     * @param id
     */
    fun delete(id: Long)

    /**
     * 获得角色(只能看到自己拥有的角色)

     * @return
     */
    fun roles(): List<Role>

    /**
     * 获得角色

     * @return
     */
    fun allRole(): List<Role>

    /**
     * 为角色授权

     * @param role
     */
    fun grant(role: Role)


    fun tables(roleVO: RoleVO): DataTable<Role>

    fun tree(): List<Role>
}
