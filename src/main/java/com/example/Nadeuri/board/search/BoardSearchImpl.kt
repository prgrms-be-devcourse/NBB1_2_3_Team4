package com.example.Nadeuri.board.search

import com.example.Nadeuri.board.BoardEntity
import com.example.Nadeuri.board.QBoardEntity
import com.example.Nadeuri.board.dto.BoardDTO
import com.querydsl.core.types.Projections
import com.querydsl.jpa.JPQLQuery
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

class BoardSearchImpl : QuerydslRepositorySupport(BoardEntity::class.java), BoardSearch {
    override fun pageDTO(pageable: Pageable): Page<BoardDTO> {
        val board: QBoardEntity = QBoardEntity.boardEntity

        val query: JPQLQuery<BoardEntity> = from(board)
        query.where(board.id.gt(0L), board.deletedAt.isNull) // deletedAt이 널인것만 = 삭제되지 않은 것만

        getQuerydsl().applyPagination(pageable, query) // 페이징 적용
        val dtoQuery: JPQLQuery<BoardDTO> = query.select(Projections.constructor(BoardDTO::class.java, board))
        // 생성자 방식 projections
        val boardDTOList: List<BoardDTO> = dtoQuery.fetch() // 쿼리 실행
        val count: Long = query.fetchCount() // 레코드 수 조회

        return PageImpl(boardDTOList, pageable, count)
    }

    override fun pageSearch(keyword: String, pageable: Pageable): Page<BoardDTO> {
        val board: QBoardEntity = QBoardEntity.boardEntity

        val query: JPQLQuery<BoardEntity> = from(board)
        query.where(board.boardTitle.containsIgnoreCase(keyword)
            .or(board.member.userId.containsIgnoreCase(keyword)),
            board.deletedAt.isNull) // deletedAt이 널인것만 = 삭제되지 않은 것만
        // 키워드가 포함된 제목 필드들만 검색

        getQuerydsl().applyPagination(pageable, query)
        val dtoQuery: JPQLQuery<BoardDTO> = query.select(Projections.constructor(BoardDTO::class.java, board))

        val boardDTOList: List<BoardDTO> = dtoQuery.fetch()

        val count: Long = query.fetchCount()

        return PageImpl(boardDTOList, pageable, count)
    }

    override fun pageFindBoardIsNotNull(pageable: Pageable): Page<BoardDTO> {
        val board: QBoardEntity = QBoardEntity.boardEntity

        val query: JPQLQuery<BoardEntity> = from(board)
        query.where(board.id.gt(0L), board.deletedAt.isNotNull) // deletedAt이 널인것만 = 삭제되지 않은 것만

        getQuerydsl().applyPagination(pageable, query) // 페이징 적용
        val dtoQuery: JPQLQuery<BoardDTO> = query.select(Projections.constructor(BoardDTO::class.java, board))
        // 생성자 방식 projections
        val boardDTOList: List<BoardDTO> = dtoQuery.fetch() // 쿼리 실행
        val count: Long = query.fetchCount() // 레코드 수 조회

        return PageImpl(boardDTOList, pageable, count)
    }
}