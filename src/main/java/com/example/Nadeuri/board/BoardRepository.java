package com.example.Nadeuri.board;

import com.example.Nadeuri.board.search.BoardSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity,Long> , BoardSearch {

}
