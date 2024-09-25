package com.example.Nadeuri.board.search;

import com.example.Nadeuri.board.BoardEntity;
import com.example.Nadeuri.board.QBoardEntity;
import com.example.Nadeuri.board.dto.BoardDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {
    public BoardSearchImpl() {
        super(BoardEntity.class);
    }

    @Override
    public Page<BoardEntity> page(Pageable pageable) {
        QBoardEntity board = QBoardEntity.boardEntity;

        JPQLQuery<BoardEntity> query = from(board);
        query.where(board.id.gt(0L));

        getQuerydsl().applyPagination(pageable, query); //페이징 적용
        List<BoardEntity> boardEntityList = query.fetch(); //쿼리 실행
        long count = query.fetchCount();  //레코드 수 조회

        return new PageImpl<>(boardEntityList, pageable, count);
    }

    @Override
    public Page<BoardDTO> pageDTO(Pageable pageable) {
        QBoardEntity board = QBoardEntity.boardEntity;

        JPQLQuery<BoardEntity> query = from(board);
        query.where(board.id.gt(0L));

        getQuerydsl().applyPagination(pageable, query); //페이징 적용
        JPQLQuery<BoardDTO> dtoQuery =
                query.select(Projections.constructor(BoardDTO.class,board));
        //생성자 방식 projections
        List<BoardDTO> boardDTOList = dtoQuery.fetch(); //쿼리 실행
        long count = query.fetchCount();  //레코드 수 조회

        return new PageImpl<>(boardDTOList, pageable, count);
    }

    @Override
    public Page<BoardDTO> pageSearch(String keyword, Pageable pageable) {
        QBoardEntity board = QBoardEntity.boardEntity;

        JPQLQuery<BoardEntity> query = from(board);
        query.where(board.boardTitle.containsIgnoreCase(keyword));

        getQuerydsl().applyPagination(pageable, query);
        JPQLQuery<BoardDTO> dtoQuery = query.select(
                Projections.constructor(BoardDTO.class,board));


        List<BoardDTO> boardDTOList = dtoQuery.fetch();

        long count = query.fetchCount();

        return new PageImpl<>(boardDTOList, pageable, count);
    }
}