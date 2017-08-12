package com.dmc.service.impl

import com.dmc.mapper.ResourceMapper
import com.dmc.mapper.RoleMapper
import com.dmc.mapper.UserMapper
import com.dmc.model.Menu
import com.dmc.model.Resource
import com.dmc.service.ResourceService
import com.dmc.util.AppConst
import com.dmc.util.SessionUtil
import com.dmc.util.id.IdUtil
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.ArrayList
import java.util.HashMap
import java.util.concurrent.CopyOnWriteArrayList
import javax.inject.Inject

/**
 *
 * @author yangfan
 * @date 2017/08/11
 */
@Service
@Transactional
class ResourceServiceImpl @Inject constructor(
        private val resourceMapper: ResourceMapper,
        private val userMapper: UserMapper,
        private val roleMapper: RoleMapper
): ResourceService {
    /**
     * 获得资源树(资源类型为菜单类型)

     * 通过用户ID判断，他能看到的资源

     * @return
     */
    override fun menus(): List<Menu> {
        val menuList = ArrayList<Menu>()

        val params = HashMap<String, Any?>()
        params.put("type", AppConst.RESOURCE_TYPE_MENU)// 菜单类型的资源
        params.put("userId", SessionUtil.currUid)// 只查自己有权限的资源

        val resourceList = resourceMapper.getResourceList(params)

        assembleMenu(menuList, resourceList)
        return menuList
    }

    /**
     * 获得资源树(包括所有资源类型)

     * 通过用户ID判断，他能看到的资源

     * @return
     */
    override fun allMenus(): List<Menu> {

        val menuList = ArrayList<Menu>()

        val params = HashMap<String, Any>()
        val resourceList = resourceMapper.getResourceList(params)
        assembleMenu(menuList, resourceList)
        return menuList
    }

    /**
     * 获得资源列表


     * @return
     */
    override fun treeList(): List<Resource> {
        val params = HashMap<String, Any>()
        val currUid = SessionUtil.currUid

        if (currUid != null) {
            params.put("userId", currUid)// 自查自己有权限的资源
        }

        var resourceList: MutableList<Resource> = resourceMapper.getResourceList(params)

        val map = HashMap<Long, Resource>()
        resourceList.forEach { resource -> map.put(resource.id, resource) }
        resourceList.forEach { resource -> resource.pname = map[resource.pid]?.pname ?:"" }

        val list = ArrayList<Resource>()

        resourceList = CopyOnWriteArrayList(resourceList)
        for (resource in resourceList) {
            if (resource.pid == null) {
                list.add(resource)
                resourceList.remove(resource)
                treeSort(resourceList, list, resource)
            }
        }


        return list
    }


    private fun treeSort(resourceList: MutableList<Resource>, list: MutableList<Resource>, parent: Resource) {

        for (resource in resourceList) {
            if (parent.id == resource.pid) {
                list.add(resource)
                resourceList.remove(resource)
                treeSort(resourceList, list, resource)
            }
        }
    }
    /**
     * 添加资源

     * @param resource
     */
    override fun add(resource: Resource) {
        resource.id = IdUtil.generateId()
        resourceMapper.save(resource)

        // 由于当前用户所属的角色，没有访问新添加的资源权限，所以在新添加资源的时候，将当前资源授权给当前用户的所有角色，以便添加资源后在资源列表中能够找到
        val userId = SessionUtil.currUid
        val roleIds = userMapper.getUserRoleIds(userId)
        roleIds.forEach { roleId -> roleMapper.saveRoleResources(roleId, listOf(resource.id)) }

    }

    /**
     * 删除资源

     * @param id
     */
    override fun delete(id: Long) = resourceMapper.deleteById(id)

    /**
     * 修改资源

     * @param resource
     */
    override fun edit(resource: Resource) = resourceMapper.update(resource)

    /**
     * 获得一个资源

     * @param id
     * *
     * @return
     */
    override fun get(id: Long): Resource = resourceMapper.getById(id)

    /**
     * 资列表
     * @param params
     * *
     * @return
     */
    override fun getResourceList(params: Map<String, Any>) = resourceMapper.getResourceList(params)


    private fun assembleMenu(menuList: MutableList<Menu>, resourceList: List<Resource>) {
        for (r in resourceList) {
            if (r.pid == null) {
                val menu = Menu()
                BeanUtils.copyProperties(r, menu)
                menu.text = r.name
                val children = ArrayList<Menu>()
                for (r1 in resourceList) {
                    if (r1.pid == r.id) {
                        val child = Menu()
                        BeanUtils.copyProperties(r1, child)
                        child.text = r1.name
                        children.add(child)
                    }
                }
                menu.children = children
                menuList.add(menu)
            }
        }
    }
}