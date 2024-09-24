package com.example.Nadeuri.board;

import com.example.Nadeuri.board.dto.BoardDTO;
import com.example.Nadeuri.board.dto.request.BoardCreateRequest;
import com.example.Nadeuri.board.exception.BoardException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final ImageRepository imageRepository;

    /**
     * 게시글 등록
     */
    @Transactional
    public void register(final BoardCreateRequest request, final MultipartFile boardImage) {
        try{
            String imageUrl = imageRepository.upload(boardImage);

            BoardEntity board = BoardEntity.create(
                    request.getMemberId(),
                    request.getBoardTitle(),
                    request.getBoardContent(),
                    request.getCategory(),
                    imageUrl
            );
            boardRepository.save(board);
        } catch (Exception e) {
            throw new IllegalArgumentException("[ERROR] 게시글 등록에 실패했습니다.");
        }
    }

    //게시글 상세 조회 (1개 조회)
    public BoardDTO read(Long boardId){
        BoardEntity board = boardRepository.findById(boardId).orElseThrow(BoardException.NOT_FOUND::get);

        return new BoardDTO(board);
    }
}
