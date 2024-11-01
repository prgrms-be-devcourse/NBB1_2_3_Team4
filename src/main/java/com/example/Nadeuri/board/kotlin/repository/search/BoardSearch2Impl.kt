package com.example.Nadeuri.board.kotlin.repository.search

import com.example.Nadeuri.board.kotlin.entity.BoardEntity
import com.example.Nadeuri.board.kotlin.controller.dto.BoardReadResponse2
import com.example.Nadeuri.board.kotlin.entity.QBoardEntity
import com.querydsl.core.types.Projections
import com.querydsl.jpa.JPQLQuery
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

class BoardSearch2Impl : QuerydslRepositorySupport(BoardEntity::class.java), BoardSearch2 {
    override fun pageDTO(pageable: Pageable): Page<BoardReadResponse2> {
        val board: QBoardEntity = QBoardEntity.boardEntity

        val query: JPQLQuery<BoardEntity> = from(board)
        query.where(board.id.gt(0L), board.deletedAt.isNull) // deletedAt이 널인것만 = 삭제되지 않은 것만

        getQuerydsl().applyPagination(pageable, query) // 페이징 적용
        val dtoQuery: JPQLQuery<BoardReadResponse2> = query.select(Projections.constructor(BoardReadResponse2::class.java, board))
        // 생성자 방식 projections
        val boardDTOList: List<BoardReadResponse2> = dtoQuery.fetch() // 쿼리 실행
        val count: Long = query.fetchCount() // 레코드 수 조회

        return PageImpl(boardDTOList, pageable, count)
    }

    override fun pageSearch(keyword: String, pageable: Pageable): Page<BoardReadResponse2> {
        val board: QBoardEntity = QBoardEntity.boardEntity

        val query: JPQLQuery<BoardEntity> = from(board)
        query.where(board.boardTitle.containsIgnoreCase(keyword)
            .or(board.member.userId.containsIgnoreCase(keyword)),
            board.deletedAt.isNull) // deletedAt이 널인것만 = 삭제되지 않은 것만
        // 키워드가 포함된 제목 필드들만 검색

        getQuerydsl().applyPagination(pageable, query)
        val dtoQuery: JPQLQuery<BoardReadResponse2> = query.select(Projections.constructor(BoardReadResponse2::class.java, board))

        val boardDTOList: List<BoardReadResponse2> = dtoQuery.fetch()

        val count: Long = query.fetchCount()

        return PageImpl(boardDTOList, pageable, count)
    }

    override fun pageFindBoardIsNotNull(pageable: Pageable): Page<BoardReadResponse2> {
        val board: QBoardEntity = QBoardEntity.boardEntity

        val query: JPQLQuery<BoardEntity> = from(board)
        query.where(board.id.gt(0L), board.deletedAt.isNotNull) // deletedAt이 널인것만 = 삭제되지 않은 것만

        getQuerydsl().applyPagination(pageable, query) // 페이징 적용
        val dtoQuery: JPQLQuery<BoardReadResponse2> = query.select(Projections.constructor(BoardReadResponse2::class.java, board))
        // 생성자 방식 projections
        val boardDTOList: List<BoardReadResponse2> = dtoQuery.fetch() // 쿼리 실행
        val count: Long = query.fetchCount() // 레코드 수 조회

        return PageImpl(boardDTOList, pageable, count)
    }
}