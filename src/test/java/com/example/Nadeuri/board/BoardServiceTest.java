package com.example.Nadeuri.board;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.Nadeuri.board.dto.request.BoardCreateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@DisplayName("[Board] 서비스 통합 테스트")
@Transactional
@AutoConfigureMockMvc  // 테스트 환경에서 MockMvc를 자동으로 설정해주는 기능 제공
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BoardServiceTest {

    @Autowired
    private LocalImageRepository localImageRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc; // 웹 서버를 실행하지 않고도 HTTP 요청을 모의하여 응답을 검증

    @Value("${file.local.upload.path}")
    private String uploadPath;

    @AfterEach
    public void down() throws IOException {
        Path path = Paths.get(uploadPath);
        localImageRepository.deleteDirectory(path);
    }

    @DisplayName("게시판 등록 성공")
    @Test
    void registerBoard_successfully() throws Exception {
        // given
        BoardCreateRequest request = new BoardCreateRequest(
                1L,
                "게시글 제목",
                "게시글 내용",
                Category.FOOD
        );
        String requestBody = objectMapper.writeValueAsString(request);

        MockMultipartFile imageFile = new MockMultipartFile(
                "image",
                "테스트_이미지.png",
                MediaType.IMAGE_JPEG_VALUE,
                "test image content".getBytes()
        );

        MockMultipartFile jsonFile = new MockMultipartFile(
                "request",
                "",
                MediaType.APPLICATION_JSON_VALUE,
                requestBody.getBytes()
        );

        // when & then
        mockMvc.perform(multipart("/v1/boards")
                        .file(imageFile)
                        .file(jsonFile))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusMessage").value("성공"));
    }
}