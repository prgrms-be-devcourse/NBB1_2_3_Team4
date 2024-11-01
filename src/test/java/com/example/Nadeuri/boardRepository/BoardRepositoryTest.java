package com.example.Nadeuri.boardRepository;

import com.example.Nadeuri.board.kotlin.entity.BoardEntity;
import com.example.Nadeuri.board.BoardRepository;
import com.example.Nadeuri.board.ImageRepository;
import com.example.Nadeuri.board.dto.BoardDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 실제 DB로 테스트
@Transactional(propagation =  Propagation.NOT_SUPPORTED) // 트랜잭션을 강제로 사용안하도록, 읽기전용이나 외부에서 트랜잭션을 할때 유용
@DisplayName("보드 단위 테스트")
@Log4j2
public class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;
    private ImageRepository imageRepository;

    @Test
    @Transactional
    public void testRead(){ //select
        Long boardId = 1L;
        Optional<BoardEntity> foundBoard = boardRepository.findById(boardId);
        assertTrue(foundBoard.isPresent(),"foundBoard should be present");
        BoardEntity boardEntity = foundBoard.get();
        System.out.println(boardEntity.toString());


        assertNotNull(boardEntity);
    }
    @Test
    public void testPage(){//page로 받기
        Pageable pageable = PageRequest.of(0,10, Sort.by("id").descending());

        Page<BoardDTO> boardEntityPage = boardRepository.pageDTO(pageable);

        assertNotNull(boardEntityPage);
//        assertEquals(10,boardEntityPage.getTotalElements());
//        assertEquals(1,boardEntityPage.getTotalPages());
//        assertEquals(0,boardEntityPage.getNumber());
//        assertEquals(10,boardEntityPage.getSize());
//        assertEquals(10,boardEntityPage.getContent().size());

        boardEntityPage.getContent().forEach(System.out::println);
    }

    @Test
    public void testPageIsNotNull(){//삭제한 게시물 전체 조회
        Pageable pageable = PageRequest.of(0,10, Sort.by("id").descending());

        Page<BoardDTO> boardEntityPage = boardRepository.pageFindBoardIsNotNull(pageable);

        assertNotNull(boardEntityPage);
//        assertEquals(10,boardEntityPage.getTotalElements());
//        assertEquals(1,boardEntityPage.getTotalPages());
//        assertEquals(0,boardEntityPage.getNumber());
//        assertEquals(10,boardEntityPage.getSize());
//        assertEquals(10,boardEntityPage.getContent().size());

        boardEntityPage.getContent().forEach(System.out::println);
    }

    @Test
    public void testPageSerach(){//page로 받기
        Pageable pageable = PageRequest.of(0,10, Sort.by("id").descending());
        String name = "검색";
        Page<BoardDTO> boardEntityPage = boardRepository.pageSearch(name,pageable);
        assertNotNull(boardEntityPage);
        log.info(boardEntityPage.getContent());

        boardEntityPage.getContent().forEach(System.out::println);
    }
}
