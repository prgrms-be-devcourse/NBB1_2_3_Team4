package com.example.Nadeuri.board.kotlin.repository

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths
import java.util.UUID


@Repository
class LocalImageRepository2 : ImageRepository2 {

    @Value("\${file.local.upload.path}")
    private lateinit var uploadPath: String

    override fun upload(file: MultipartFile): String {
        val uuid = UUID.randomUUID().toString()
        val originalName = file.originalFilename
        val savePath = Paths.get(uploadPath, uuid + "_" + originalName)

        try {
            Files.createDirectories(savePath.parent)
            file.transferTo(savePath)
        } catch (e: Exception) {
            throw IllegalArgumentException("[Error] 이미지 업로드에 실패했습니다.")
        }
        return uuid
    }
}