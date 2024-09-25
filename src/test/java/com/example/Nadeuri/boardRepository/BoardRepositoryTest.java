package com.example.Nadeuri.boardRepository;

import com.example.Nadeuri.board.BoardEntity;
import com.example.Nadeuri.board.BoardRepository;
import com.example.Nadeuri.board.Category;
import com.example.Nadeuri.board.ImageRepository;
import com.example.Nadeuri.board.dto.BoardDTO;
import com.example.Nadeuri.board.dto.request.BoardCreateRequest;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation =  Propagation.NOT_SUPPORTED)
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

        Page<BoardEntity> boardEntityPage = boardRepository.page(pageable);
        assertNotNull(boardEntityPage);
        assertEquals(10,boardEntityPage.getTotalElements());
        assertEquals(1,boardEntityPage.getTotalPages());
        assertEquals(0,boardEntityPage.getNumber());
        assertEquals(10,boardEntityPage.getSize());
        assertEquals(10,boardEntityPage.getContent().size());

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