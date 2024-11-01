package com.example.Nadeuri.member.security.util

import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

object CookieUtil {  // 싱글톤 객체로 정의

    fun addCookie(response: HttpServletResponse, name: String, value: String, maxAge: Int) {
        val cookie = Cookie(name, value)
        cookie.path = "/"
        cookie.maxAge = maxAge

        response.addCookie(cookie)
    }

    fun deleteCookie(request: HttpServletRequest, response: HttpServletResponse, name: String) {
        val cookies = request.cookies ?: return

        for (cookie in cookies) {
            if (name == cookie.name) {
                cookie.value = ""
                cookie.path = "/"
                cookie.maxAge = 0
                response.addCookie(cookie)
            }
        }
    }

//    fun serialize(obj: Any): String {
//        return Base64.getUrlEncoder()
//            .encodeToString(SerializationUtils.serialize(obj))
//    }
//
//    fun <T> deserialize(cookie: Cookie, cls: Class<T>): T {
//        return cls.cast(
//            SerializationUtils.deserialize(
//                Base64.getUrlDecoder().decode(cookie.value)
//            )
//        )
//    }
}
