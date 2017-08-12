package com.dmc.service

import com.dmc.model.User
import com.dmc.vo.DataTable
import com.dmc.vo.UserVO

/**
 *
 * @author yangfan
 * @date 2017/08/11
 */
interface UserService {

    /**
     * 用户登录

     * @param user 里面包含登录名和密码
     * *
     * @return 用户对象
     */
    fun login(user: User): User?


    /**
     * 添加用户

     * @param user
     */
    fun add(user: User)

    /**
     * 获得用户对象

     * @param id
     * *
     * @return
     */
    fun get(id: Long): User?

    /**
     * 编辑用户

     * @param user
     */
    fun edit(user: User)

    /**
     * 删除用户

     * @param id
     */
    fun delete(id: Long)

    /**
     * 用户授权

     * @param user 需要user.roleIds的属性值
     */
    fun grant(user: User)

    /**
     * 获得用户能访问的资源地址

     * @param id 用户ID
     * *
     * @return
     */
    fun resourceList(id: Long): List<String>

    /**
     * 编辑用户密码

     * @param user
     */
    fun editPwd(user: User)

    /**
     * 修改用户自己的密码

     * @param currUid
     * *
     * @param oldPwd
     * *
     * @param pwd
     * *
     * @return
     */
    fun editCurrentUserPwd(currUid: Long, oldPwd: String, pwd: String): Boolean


    fun getUserRoleNames(id: Long): List<String>

    fun tables(userVO: UserVO): DataTable<User>
}