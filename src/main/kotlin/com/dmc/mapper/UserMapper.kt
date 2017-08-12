package com.dmc.mapper

import com.dmc.model.User
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

/**
 *
 * @author yangfan
 * @date 2017/08/11
 */
@Mapper
interface UserMapper : tk.mybatis.mapper.common.Mapper<User>{
    /**
     * 登录
     * @param params 参数，用户名和密码
     * *
     * @return 用户
     */
    fun login(params: Map<String, Any?>): User?

    /**
     * 统计用户名
     * @param name 用户名
     * *
     * @return 数量
     */
    fun countUserName(name: String): Int

    /**
     * 新增用户
     * @param user 用户
     */
    fun save(user: User)

    /**
     * 查询用户
     * @param params 查询参数
     * *
     * @return
     */
    fun findUser(params: Map<String, Any>): List<User>

    /**
     * 获得用户的角色
     * @param id 用户ID
     * *
     * @return 角色ID
     */
    fun getUserRoleIds(id: Long): List<Long>

    /**
     * 获得用户的角色名字
     * @param id 用户ID
     * *
     * @return 角色名称
     */
    fun getUserRoleNames(id: Long): List<String>

    /**
     * ID查询用户
     * @param id ID
     * *
     * @return 用户
     */
    fun getById(id: Long): User?

    /**
     * 更新用户
     * @param user 用户
     */
    fun update(user: User)

    /**
     * 删除用户
     * @param id 用户ID
     */
    fun deleteById(id: Long)

    /**
     * 删除用户的角色
     * @param id 用户ID
     */
    fun deleteRoles(id: Long)

    /**
     * 保存用户角色
     * @param id 用户Id
     * *
     * @param roleIds 角色
     */
    fun saveRoles(@Param("id") id: Long?, @Param("roleIds") roleIds: List<Long>)
}