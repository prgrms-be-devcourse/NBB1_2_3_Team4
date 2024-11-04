package com.example.Nadeuri.likes.controller.dto.response

import com.example.Nadeuri.likes.entity.LikeEntity

data class LikeDeleteResponse(
    val likeId: Long,
) {
    companion object {
        fun from(like: LikeEntity): LikeDeleteResponse =
            LikeDeleteResponse(
                likeId = like.id
            )
    }
}