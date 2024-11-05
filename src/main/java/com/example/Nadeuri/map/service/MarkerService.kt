package com.example.Nadeuri.map.service

import com.example.Nadeuri.map.entity.MarkerEntity
import com.example.Nadeuri.map.repository.MarkerRepository
import org.springframework.stereotype.Service

@Service
class MarkerService(private val markerRepository: MarkerRepository) {

    suspend fun saveMarker(markerEntity: MarkerEntity): MarkerEntity {
        return markerRepository.save(markerEntity)
    }

    suspend fun getAllMarkers(): List<MarkerEntity> {
        return markerRepository.findAll()
    }
}
