package com.example.Nadeuri.board.kotlin.controller.dto

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

data class BoardPageRequestDTO2(
    @field:Min(1)
    var page: Int = 1,

    @field:Min(10)
    @field:Max(100)
    var size: Int = 10
) {
    fun getPageable(sort: Sort): Pageable {
        val adjustedPageNumber = if (page < 0) 1 else page - 1
        val adjustedSizeNumber = if (size <= 10) 10 else size

        return PageRequest.of(adjustedPageNumber, adjustedSizeNumber, sort)
    }
}