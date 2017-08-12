/*
 * Copyright (c) 2016 xiaomaihd and/or its affiliates.All Rights Reserved.
 *            http://www.xiaomaihd.com
 */
package com.dmc.util

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object JsonUtil {

    val objectMapper: ObjectMapper

    init {
        objectMapper = ObjectMapper()
        //去掉默认的时间戳格式
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        //设置为中国上海时区
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"))
        objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false)
        //空值不序列化
        objectMapper.setSerializationInclusion(Include.NON_NULL)
        //反序列化时，属性不存在的兼容处理
        objectMapper.deserializationConfig.withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        //序列化时，日期的统一格式
        objectMapper.dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        //单引号处理
        objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
    }

    fun <T> toObject(json: String, clazz: Class<T>): T {
        try {
            return objectMapper.readValue(json, clazz)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

    }

    fun <T> toJsonString(entity: T): String {
        try {
            return objectMapper.writeValueAsString(entity)
        } catch (e: JsonProcessingException) {
            throw RuntimeException(e)
        }

    }

    fun <T> toCollection(json: String, typeReference: TypeReference<T>): T {
        try {
            return objectMapper.readValue<T>(json, typeReference)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

    }

    fun <T> bean2Map(bean: Any): Map<*, *>? {
        try {
            return objectMapper.convertValue(bean, Map::class.java)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return null
    }

    fun <T> map2Bean(map: Map<*, *>, clazz: Class<T>): T {
        return objectMapper.convertValue(map, clazz)
    }

}