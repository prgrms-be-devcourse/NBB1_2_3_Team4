package com.example.Nadeuri.map.dto

data class MarkerRequestDTO(
    val latitude: Double,
    val longitude: Double,
    val description: String? = null,
    val boardId: Long? = null // 마커가 속한 게시글 ID
)