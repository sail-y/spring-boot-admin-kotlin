/**
 * pursuit of excellence;striving for excellence;insist on keeping improving, accomplish it best.
 * copyright keeping all right reserved
 */
package com.dmc.conf

import com.dmc.exception.BusinessException
import com.dmc.model.RestResp
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletRequest
import javax.validation.ConstraintViolationException
import javax.validation.ValidationException

@ControllerAdvice
@ResponseBody
class GlobalDefaultExceptionHandler {

    val log: Logger = LoggerFactory.getLogger(this::class.java)


    /**
     * 400 - Bad Request
     */
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(req: HttpServletRequest, e: HttpMessageNotReadableException): RestResp<String> {
        log.error(e.message, e)

        return RestResp.error(RestResp.ERROR, e.message?:"")
    }

    /**
     * 400 - Bad Request
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): RestResp<String> {

        val result = e.bindingResult
        val error = result.fieldError
        val field = error.field
        val code = error.defaultMessage
        val message = String.format("%s:%s", field, code)

        return RestResp.error(RestResp.ERROR, message)
    }

    /**
     * 400 - Bad Request
     */

    @ExceptionHandler(BindException::class)
    fun handleBindException(e: BindException): RestResp<String> {
        log.error(e.message, e)

        val result = e.bindingResult
        val error = result.fieldError
        val field = error.field
        val code = error.defaultMessage
        val message = String.format("%s:%s", field, code)

        return RestResp.error(RestResp.ERROR, message)

    }

    /**
     * 400 - Bad Request
     */

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleServiceException(e: ConstraintViolationException): RestResp<String> {
        log.error(e.message, e)
        val violations = e.constraintViolations
        val violation = violations.iterator().next()
        val message = violation.message


        return RestResp.error(RestResp.ERROR, message)
    }

    /**
     * 400 - Bad Request
     */

    @ExceptionHandler(ValidationException::class)
    fun handleValidationException(e: ValidationException): RestResp<String> {
        log.error(e.message, e)

        return RestResp.error(RestResp.ERROR, e.message?:"")

    }

    /**
     * 400 - Bad Request
     */

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException): RestResp<String> {
        log.error(e.message, e)

        return RestResp.error(RestResp.ERROR, e.message?:"")

    }

    /**
     * 400 - Bad Request
     */

    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalStateException(e: IllegalStateException): RestResp<String> {
        log.error(e.message, e)

        return RestResp.error(RestResp.ERROR, e.message?:"")

    }

    /**
     * 405 - Method Not Allowed
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleHttpRequestMethodNotSupportedException(e: HttpRequestMethodNotSupportedException): RestResp<String> {
        log.error("不支持当前请求方法", e)


        return RestResp.error(RestResp.ERROR, "不支持当前请求方法")

    }

    /**
     * 415 - Unsupported Media Type
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException::class)
    fun handleHttpMediaTypeNotSupportedException(e: Exception): RestResp<String> {
        log.error("mediatype_not_support", e)

        return RestResp.error(RestResp.ERROR, "mediatype_not_support")

    }


    @ExceptionHandler(BusinessException::class)
    fun businessException(e: BusinessException): RestResp<String> {
        log.error(e.message, e)
        return RestResp.error(e.code, e.message?:"")
    }


    @ExceptionHandler(Exception::class)
    fun allException(e: Exception): RestResp<String> {
        log.error("系统异常", e)
        return RestResp.error(RestResp.ERROR, "内部错误，请联系管理员。")
    }

}
