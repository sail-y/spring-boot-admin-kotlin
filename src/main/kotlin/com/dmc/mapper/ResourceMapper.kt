package com.dmc.mapper

import com.dmc.model.Resource
import org.apache.ibatis.annotations.Mapper

/**
 * Created by yangfan on 2015/5/2.
 * 资源操作Mapper
 */
@Mapper
interface ResourceMapper : tk.mybatis.mapper.common.Mapper<Resource> {

    /**
     * 获取资源列表

     * @param params 参数
     * *
     * @return
     */
    fun getResourceList(params: Map<String, Any?>): MutableList<Resource>

    /**
     * 增加资源

     * @param resource
     */
    fun save(resource: Resource)

    /**
     * 级联删除

     * @param id 主键
     */
    fun deleteById(id: Long?)

    /**
     * 编辑资源

     * @param resource 资源
     */
    fun update(resource: Resource)

    /**
     * 通过ID获得资源

     * @param id ID
     * *
     * @return 资源
     */
    fun getById(id: Long?): Resource

    /**
     * 查询角色的资源ID
     * @param roleId 角色ID
     * *
     * @return 资源ID
     */
    fun getRoleResourceIds(roleId: Long?): List<Long>

    /**
     * 角色的资源名称集合
     * @param roleId 角色ID
     * *
     * @return 资源名称
     */
    fun getRoleResourceNames(roleId: Long?): List<String>
}
