package com.example.Nadeuri.map.dto

data class MapResponseDTO(
    val documents: List<MapResult>,
    val meta: Meta
)

data class MapResult(
    val address_name: String,
    val x: String,
    val y: String
)

data class Meta(
    val total_count: Int
)