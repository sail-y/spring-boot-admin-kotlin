package com.dmc.service.impl

import com.dmc.mapper.ResourceMapper
import com.dmc.mapper.RoleMapper
import com.dmc.mapper.UserMapper
import com.dmc.model.Role
import com.dmc.service.RoleService
import com.dmc.util.SessionUtil
import com.dmc.util.id.IdUtil
import com.dmc.vo.DataTable
import com.dmc.vo.RoleVO
import com.github.pagehelper.Page
import com.github.pagehelper.PageHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import java.util.Collections
import java.util.HashMap
import javax.inject.Inject

@Service
@Transactional
class RoleServiceImpl @Inject constructor(
        private val roleMapper: RoleMapper,
        private val userMapper: UserMapper,
        private val resourceMapper: ResourceMapper

): RoleService {


    override fun add(role: Role) {
        role.id = IdUtil.generateId()
        roleMapper.save(role)

        // 刚刚添加的角色，赋予给当前的用户
        userMapper.saveRoles(SessionUtil.currUid, listOf(role.id))
    }

    override fun get(roleId: Long): Role {
        val role = roleMapper.getById(roleId)
        val resourceIds = resourceMapper.getRoleResourceIds(roleId)
        val resourceNames = resourceMapper.getRoleResourceNames(roleId)

        role!!.resourceIds = resourceIds
        role.resourceNames = resourceNames
        return role
    }

    override fun edit(role: Role) {
        roleMapper.update(role)
    }


    override fun treeGrid(): List<Role> {
        val params = HashMap<String, Any>()
        val currUid = SessionUtil.currUid
        if (currUid != null) {
            params.put("userId", currUid)// 查自己有权限的角色
        }

        val roles = roleMapper.getRoleList(params)

        roles.forEach { role ->
            val resourceIds = resourceMapper.getRoleResourceIds(role.id)
            val resourceNames = resourceMapper.getRoleResourceNames(role.id)

            role.resourceIds = resourceIds
            role.resourceNames = resourceNames
        }
        return roles
    }

    override fun delete(id: Long) {
        // PID的外键为级联删除
        roleMapper.deleteById(id)
    }


    override fun roles(): List<Role> {

        val params = HashMap<String, Any>()
        val currUid = SessionUtil.currUid
        if (currUid != null) {
            params.put("userId", currUid)// 查自己有权限的角色
        }


        return roleMapper.getRoleList(params)
    }

    override fun allRole(): List<Role> {
        return roleMapper.getRoleList(HashMap<String, Any>())
    }

    override fun grant(role: Role) {
        roleMapper.deleteRoleResources(role.id)

        if (!role.resourceIds.isEmpty()) {
            roleMapper.saveRoleResources(role.id, role.resourceIds)
        }
    }

    override fun tables(roleVO: RoleVO): DataTable<Role> {
        PageHelper.offsetPage<Any>(roleVO.start?:0, roleVO.length?:10)

        val roles = roleMapper.getRoleList(HashMap<String, Any>())
        val total = (roles as Page<*>).total
        val tables = DataTable<Role>(roleVO.draw, total, total, roles)
        return tables
    }

    override fun tree(): List<Role> {

        val params = HashMap<String, Any>()

        val roles = roleMapper.getRoleList(params)



        return roles
    }

}
