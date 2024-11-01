package com.example.Nadeuri.board.kotlin.repository

import com.example.Nadeuri.board.kotlin.entity.BoardEntity
import com.example.Nadeuri.board.kotlin.repository.search.BoardSearch2
import org.springframework.data.jpa.repository.JpaRepository

interface BoardRepository2 : JpaRepository<BoardEntity, Long>, BoardSearch2