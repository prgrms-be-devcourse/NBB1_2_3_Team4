package com.example.Nadeuri.board;

import com.example.Nadeuri.board.dto.request.BoardCreateRequest;
import com.example.Nadeuri.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/v1/boards")
@RequiredArgsConstructor
@RestController
public class BoardController {
    private final BoardService boardService;

    /**
     * 게시글 등록
     */
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResponse> register(
            @RequestPart("request") @Valid final BoardCreateRequest request,
            @RequestPart("image") final MultipartFile multipartFile
    ) {
        boardService.register(request, multipartFile);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
