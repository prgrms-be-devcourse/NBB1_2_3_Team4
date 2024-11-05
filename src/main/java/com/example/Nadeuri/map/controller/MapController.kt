package com.example.Nadeuri.map.controller

import com.example.Nadeuri.common.response.ApiResponse
import com.example.Nadeuri.map.dto.MapResponseDTO
import com.example.Nadeuri.map.entity.MarkerEntity
import com.example.Nadeuri.map.service.MapService
import com.example.Nadeuri.map.service.MarkerService
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity

@RestController
@RequestMapping("/api/map")
class MapController(
    private val mapService: MapService,
    private val markerService: MarkerService
) {

    @GetMapping("/coordinates")
    suspend fun getCoordinates(@RequestParam query: String): ApiResponse<MapResponseDTO> {
        require(query.isNotBlank()) { "Query cannot be empty" }
        val response = mapService.getCoordinates(query)
        return ApiResponse.success(response)
    }

    @PostMapping("/marker")
    suspend fun addMarker(@RequestBody markerEntity: MarkerEntity): ApiResponse<MarkerEntity> {
        val savedMarker = markerService.saveMarker(markerEntity)
        return ApiResponse.success(savedMarker)
    }

    @GetMapping("/markers")
    suspend fun getAllMarkers(): ApiResponse<List<MarkerEntity>> {
        val markers = markerService.getAllMarkers()
        return ApiResponse.success(markers)
    }
}
