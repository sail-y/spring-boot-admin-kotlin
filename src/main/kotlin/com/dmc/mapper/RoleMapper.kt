package com.dmc.mapper

import com.dmc.model.Role
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

/**
 * Created by yangfan on 2015/5/3.
 * 角色管理
 */
@Mapper
interface RoleMapper : tk.mybatis.mapper.common.Mapper<Role> {
    /**
     * 保存角色
     * @param role 角色
     */
    fun save(role: Role)

    /**
     * ID查角色
     * @param id ID
     * *
     * @return 角色
     */
    fun getById(id: Long): Role?

    /**
     * 更新角色
     * @param role 角色
     */
    fun update(role: Role)

    /**
     * 角色列表
     * @param params 参数
     * *
     * @return 角色列表
     */
    fun getRoleList(params: Map<String, Any>): List<Role>

    /**
     * 删除角色
     * @param id ID
     */
    fun deleteById(id: Long?)

    /**
     * 删除角色资源
     * @param id 角色ID
     */
    fun deleteRoleResources(id: Long?)

    /**
     * 保存角色的资源
     * @param id 角色ID
     * *
     * @param resourceIds 资源ID
     */
    fun saveRoleResources(@Param("id") id: Long?, @Param("resourceIds") resourceIds: List<Long>)
}
