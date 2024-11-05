package com.example.Nadeuri.map.repository

import com.example.Nadeuri.map.entity.MarkerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MarkerRepository : JpaRepository<MarkerEntity, Long>