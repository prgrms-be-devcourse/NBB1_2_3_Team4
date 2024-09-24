package com.example.Nadeuri.board.search;

import com.example.Nadeuri.board.BoardEntity;
import com.example.Nadeuri.board.dto.BoardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardSearch {
    Page<BoardEntity> page(Pageable pageable);
    Page<BoardDTO> pageDTO(Pageable pageable);

    Page<BoardDTO> pageSearch(String keyword, Pageable pageable);
}
