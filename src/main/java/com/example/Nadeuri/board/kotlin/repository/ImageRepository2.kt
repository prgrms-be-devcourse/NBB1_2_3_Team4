package com.example.Nadeuri.board.kotlin.repository

import org.springframework.web.multipart.MultipartFile

interface ImageRepository2 {
    fun upload(file: MultipartFile): String
}