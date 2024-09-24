package com.example.Nadeuri.boardRepository;

import com.example.Nadeuri.board.BoardEntity;
import com.example.Nadeuri.board.BoardRepository;
import com.example.Nadeuri.board.Category;
import com.example.Nadeuri.board.ImageRepository;
import com.example.Nadeuri.board.dto.request.BoardCreateRequest;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


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
}
