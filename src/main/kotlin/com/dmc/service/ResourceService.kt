package com.dmc.service

import com.dmc.model.Menu
import com.dmc.model.Resource

/**
 *
 * @author yangfan
 * @date 2017/08/11
 */
interface ResourceService {

    /**
     * 获得资源树(资源类型为菜单类型)

     * 通过用户ID判断，他能看到的资源

     * @return
     */
    fun menus(): List<Menu>

    /**
     * 获得资源树(包括所有资源类型)

     * 通过用户ID判断，他能看到的资源

     * @return
     */
    fun allMenus(): List<Menu>

    /**
     * 获得资源列表


     * @return
     */
    fun treeList(): List<Resource>

    /**
     * 添加资源

     * @param resource
     */
    fun add(resource: Resource)

    /**
     * 删除资源

     * @param id
     */
    fun delete(id: Long)

    /**
     * 修改资源

     * @param resource
     */
    fun edit(resource: Resource)

    /**
     * 获得一个资源

     * @param id
     * *
     * @return
     */
    operator fun get(id: Long): Resource

    /**
     * 资列表
     * @param params
     * *
     * @return
     */
    fun getResourceList(params: Map<String, Any>): List<Resource>

}