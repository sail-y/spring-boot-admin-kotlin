package com.dmc.controller

import com.dmc.model.Menu
import com.dmc.model.Resource
import com.dmc.model.RestResp
import com.dmc.service.ResourceService
import org.springframework.web.bind.annotation.*
import javax.inject.Inject

/**
 *
 * @author yangfan
 * @date 2017/08/11
 */
@RestController
@RequestMapping("/resource")
class ResourceController @Inject constructor(
        private val resourceService: ResourceService
) {

    /**
     * 获得菜单
     * <p>
     * 通过用户ID判断，他能看到的菜单
     *
     * @return
     */
    @PostMapping("/menus")
    fun menus(): List<Menu> {
        return resourceService.menus()
    }

    /**
     * 资源详情
     */
    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: Long): Resource {
        return resourceService.get(id)
    }

    /**
     * 获得资源树(包括所有资源类型)
     *
     *
     * 通过用户ID判断，他能看到的资源

     * @return
     */
    @PostMapping("/allMenus")
    fun allMenus(): List<Menu> {

        return resourceService.allMenus()
    }

    /**
     * 添加资源
     */
    @PostMapping
    fun add(@RequestBody resource: Resource): RestResp<*> {
        resourceService.add(resource)
        return RestResp.ok("添加成功")
    }

    /**
     * 编辑资源
     */
    @PutMapping
    fun edit(@RequestBody resource: Resource): RestResp<*> {
        resourceService.edit(resource)

        return RestResp.ok("编辑成功")
    }

    /**
     * 删除资源
     */
    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Long): RestResp<*> {
        resourceService.delete(id)

        return RestResp.ok("删除成功")
    }


    /**
     * tree型列表
     * @return
     */
    @GetMapping("/treeList")
    fun treeList(): List<Resource> {
        return resourceService.treeList()
    }



}