package com.dmc.controller

import com.dmc.model.RestResp
import com.dmc.model.Role
import com.dmc.service.RoleService
import com.dmc.vo.DataTable
import com.dmc.vo.RoleVO
import org.springframework.web.bind.annotation.*
import javax.inject.Inject

/**
 * 角色控制器

 * @author yangfan
 */
@RestController
@RequestMapping("/role")
class RoleController @Inject constructor(
        private val roleService: RoleService
) {


    /**
     * 添加角色

     * @return
     */
    @PostMapping
    fun add(@RequestBody role: Role): RestResp<*> {
        roleService.add(role)
        return RestResp.ok("添加成功！")
    }

    /**
     * 修改角色

     * @param role
     * *
     * @return
     */
    @PutMapping
    fun edit(@RequestBody role: Role): RestResp<*> {
        roleService.edit(role)
        return RestResp.ok("更新成功！")
    }

    @PostMapping("/tables")
    fun tables(@RequestBody roleVO: RoleVO): DataTable<Role> {
        return roleService.tables(roleVO)
    }

    @GetMapping("/tree")
    fun tree(): List<Role> {
        return roleService.tree()
    }

    /**
     * get role by id

     * @param roleId
     * *
     * @return
     */
    @GetMapping("/{roleId}")
    fun getById(@PathVariable("roleId") roleId: Long): Role {

        return roleService.get(roleId)
    }

    /**
     * 删除角色

     * @param roleId
     * *
     * @return
     */
    @DeleteMapping("/{roleId}")
    fun delete(@PathVariable("roleId") roleId: Long): RestResp<*> {
        roleService.delete(roleId)

        return RestResp.ok("删除成功！")
    }


    /**
     * 授权

     * @param role
     * *
     * @return
     */
    @PostMapping("/grant")
    fun grant(@RequestBody role: Role): RestResp<*> {

        roleService.grant(role)

        return RestResp.ok("授权成功！")
    }


}
