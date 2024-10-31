package com.example.Nadeuri.common.response

class ApiResponse<T> private constructor(
    val statusMessage: String,
    val data: T?
) {
    companion object {
        // 성공 응답
        fun <T> success(data: T): ApiResponse<T> {
            return ApiResponse("성공", data)
        }

        // 실패 응답
        fun <T> failure(errorMessage: String): ApiResponse<T?> {
            return ApiResponse("실패", null)
        }
    }
}
