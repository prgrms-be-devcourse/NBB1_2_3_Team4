package com.example.Nadeuri.likes.common

data class ApiResponse<T>(
    val statusMessage: String,
    val data: T,
) {
    companion object {
        fun <R> success(data: R) = ApiResponse("성공", data)
    }
}