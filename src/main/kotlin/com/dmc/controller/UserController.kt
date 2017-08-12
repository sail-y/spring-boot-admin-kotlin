package com.dmc.controller

import com.dmc.exception.BusinessException
import com.dmc.jwt.AuthTokenDetails
import com.dmc.jwt.JsonWebTokenUtility
import com.dmc.model.RestResp
import com.dmc.model.User
import com.dmc.service.UserService
import com.dmc.util.SessionUtil
import com.dmc.vo.AuthTokenVO
import com.dmc.vo.DataTable
import com.dmc.vo.UserVO
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.inject.Inject

/**
 *
 * @author yangfan
 * @date 2017/08/11
 */
@RestController
@RequestMapping("/user")
class UserController(
        @Inject private var userService: UserService
) {

    private val tokenService = JsonWebTokenUtility()

    @PostMapping("/login")
    fun login(@RequestBody u: User): AuthTokenVO {
        val user: User? = userService.login(u)


        if (user != null) {
            val userId = user.id
            val authTokenDetails = AuthTokenDetails(userId, user.username, userService.getUserRoleNames(userId), buildExpirationDate())

            val jwt = tokenService.createJsonWebToken(authTokenDetails)

            val authToken = AuthTokenVO(jwt, userId, userService.resourceList(userId), user.name)

            return authToken

        } else {
            throw BusinessException("用户名或密码错误")
        }
    }

    private fun buildExpirationDate(): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_WEEK, 1)
        return calendar.time
    }


    /**
     * 添加用户

     * @return
     */
    @PostMapping
    fun add(@RequestBody user: User): User {
        userService.add(user)
        return user
    }


    /**
     * 修改用户

     */
    @PutMapping
    fun edit(@RequestBody user: User): User {
        userService.edit(user)
        return user
    }

    @PostMapping("/tables")
    fun tables(@RequestBody userVO: UserVO): DataTable<User> {
        return userService.tables(userVO)
    }


    /**
     * 用户详情
     * @param userId
     * *
     * @return
     */
    @GetMapping("/{userId}")
    fun getUserById(@PathVariable("userId") userId: Long): User {
        val user = userService.get(userId)
        user!!.password = ""
        return user
    }

    /**
     * 删除用户

     * @param userId
     * *
     * @return
     */
    @DeleteMapping("/{userId}")
    fun delete(@PathVariable("userId") userId: Long): RestResp<*> {
        val currUid = SessionUtil.currUid
        if (userId == currUid) {// 不能删除自己
            return RestResp.error(RestResp.ERROR, "不能删除自己")
        }

        userService.delete(userId)

        return RestResp.ok("删除成功")
    }

    /**
     * 批量删除用户

     * @param ids ('0','1','2')
     * *
     * @return
     */
    @DeleteMapping("/batchDelete")
    fun batchDelete(ids: String) {
        if (ids.isNotEmpty()) {
            ids.split(",".toRegex()).dropLastWhile { it.isEmpty() }.forEach { id -> delete(id.toLong()) }
        }
    }

    /**
     * 用户授权
     */
    @PostMapping("/grant")
    fun grant(@RequestBody user: User): RestResp<*> {
        userService.grant(user)
        return RestResp.ok("授权成功！")
    }


    /**
     * 编辑用户密码

     * @param user
     * *
     * @return
     */
    @PostMapping("/editPwd")
    fun editPwd(@RequestBody user: User): RestResp<*> {
        userService.editPwd(user)
        return RestResp.ok("编辑成功")
    }


    /**
     * 修改自己的密码

     * @param user
     * *
     * @return
     */
    @PostMapping("/editCurrentUserPwd")
    fun editCurrentUserPwd(@RequestBody user: User): RestResp<*> {
        val currUid = SessionUtil.currUid

        if (currUid != null) {
            if (!userService.editCurrentUserPwd(currUid, user.oldPassword, user.password)) {
                throw RuntimeException("原密码错误！")
            }
        } else {
            throw RuntimeException("登录超时，请重新登录！")
        }

        return RestResp.ok("修改成功")
    }

}

