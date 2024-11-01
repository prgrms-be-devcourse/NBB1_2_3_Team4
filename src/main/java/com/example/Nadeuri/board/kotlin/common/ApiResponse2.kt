package com.example.Nadeuri.board.kotlin.common

data class ApiResponse2<T>(
    val statusMessage: String,
    val data: T,
) {
    companion object {
        fun <R> success(data: R) = ApiResponse2("성공", data)
    }
}