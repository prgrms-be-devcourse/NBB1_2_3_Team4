package com.example.Nadeuri.board.kotlin.controller.dto

import com.example.Nadeuri.board.kotlin.domain.Category2
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class BoardCreateRequest2(
    @NotNull(message = "회원만 게시글을 작성할 수 있습니다.")
    val memberId: Long,

    @NotBlank(message = "제목을 확인해주세요. 빈값 혹은 null 일 수 없습니다.")
    val boardTitle: String,

    @NotBlank(message = "내용을 확인해주세요. 빈값 혹은 null 일 수 없습니다.")
    val boardContent: String,

    @NotNull(message = "카테고리 선택은 필수입니다.")
    val category2: Category2
)
