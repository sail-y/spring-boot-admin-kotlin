package com.dmc.service.impl

import com.dmc.mapper.ResourceMapper
import com.dmc.mapper.UserMapper
import com.dmc.model.User
import com.dmc.service.UserService
import com.dmc.util.AppConst
import com.dmc.util.id.IdUtil
import com.dmc.vo.DataTable
import com.dmc.vo.UserVO
import com.github.pagehelper.Page
import com.github.pagehelper.PageHelper
import org.apache.commons.codec.digest.DigestUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.Assert
import org.springframework.util.StringUtils
import java.util.*
import java.util.stream.Collectors
import javax.inject.Inject

/**
 *
 * @author yangfan
 * @date 2017/08/11
 */
@Service
@Transactional
class UserServiceImpl @Inject constructor(
        private val userMapper: UserMapper,
        private val resourceMapper: ResourceMapper
) : UserService {


    override fun login(user: User): User? {
        val params = HashMap<String, Any>()
        params.put("username", user.username)
        params.put("password", DigestUtils.md5Hex(user.password))

        return userMapper.login(params)
    }


    override fun add(user: User) {
        user.id = IdUtil.generateId()
        if (userMapper.countUserName(user.username) > 0) {
            throw RuntimeException("登录名已存在！")
        } else {
            user.password = DigestUtils.md5Hex(user.password)
            userMapper.save(user)
        }
    }

    override fun get(id: Long): User? {
        val user = userMapper.getById(id)
        if (user != null) {
            user.roleIds = userMapper.getUserRoleIds(id)
        }
        return user
    }

    override fun edit(user: User) {
        if (userMapper.countUserName(user.name) > 0) {
            throw RuntimeException("登录名已存在！")
        } else {


            if (!StringUtils.isEmpty(user.password)) {
                user.password = DigestUtils.md5Hex(user.password)
            }
            userMapper.update(user)
        }
    }

    override fun delete(id: Long) {
        userMapper.deleteRoles(id)
        userMapper.deleteById(id)
    }

    override fun grant(user: User) {
        Assert.notNull(user.id, "id 不能为空")
        Assert.notEmpty(user.roleIds, "roleIds must have length; it must not be null or empty")


        userMapper.deleteRoles(user.id)
        userMapper.saveRoles(user.id, user.roleIds)
    }

    override fun resourceList(id: Long): List<String> {
        val params = HashMap<String, Any>()
        params.put("userId", id)
        params.put("type", AppConst.RESOURCE_TYPE_METHOD)
        val resources = resourceMapper.getResourceList(params)
        return resources.map { (_, _, _, _, method, _, _, _, url) -> url + "-" + method }
    }

    override fun editPwd(user: User) {
        Assert.notNull(user, "user can not be null")
        Assert.hasText(user.password, "password mush have value")

        user.password = DigestUtils.md5Hex(user.password)
        userMapper.update(user)


    }

    override fun editCurrentUserPwd(currUid: Long, oldPwd: String, pwd: String): Boolean {
        val user = userMapper.getById(currUid)
        if (user!!.password.equals(DigestUtils.md5Hex(oldPwd), ignoreCase = true)) {// 说明原密码输入正确
            user.password = DigestUtils.md5Hex(pwd)
            userMapper.update(user)
            return true
        }
        return false
    }

    override fun getUserRoleNames(id: Long): List<String> = userMapper.getUserRoleNames(id)

    override fun tables(userVO: UserVO): DataTable<User> {
        PageHelper.offsetPage<Any>(userVO.start!!, userVO.length!!)

        val users = userMapper.findUser(HashMap<String, Any>())
        val total = (users as Page<*>).total
        val tables = DataTable<User>(userVO.draw, total, total, users)
        return tables
    }

}