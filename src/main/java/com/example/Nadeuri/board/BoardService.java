package com.example.Nadeuri.board;

import com.example.Nadeuri.board.dto.BoardDTO;
import com.example.Nadeuri.board.dto.request.BoardCreateRequest;
import com.example.Nadeuri.board.dto.request.BoardPageRequestDTO;
import com.example.Nadeuri.board.dto.request.BoardUpdateRequest;
import com.example.Nadeuri.board.exception.BoardException;
import com.example.Nadeuri.member.MemberEntity;
import com.example.Nadeuri.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class BoardService {
    private static final Logger log = LoggerFactory.getLogger(BoardService.class);
    private final BoardRepository boardRepository;
    private final ImageRepository imageRepository;
    private final MemberRepository memberRepository;

    /**
     * 게시글 등록
     */
    @Transactional
    public void register(final BoardCreateRequest request, final MultipartFile boardImage) {
        try {
            String imageUrl = imageRepository.upload(boardImage);
            MemberEntity memberEntity = retrieveMember(request.getMemberId());

            BoardEntity board = BoardEntity.create(
                    memberEntity,
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
    public BoardDTO read(Long boardId) {
        BoardEntity board = boardRepository.findById(boardId).orElseThrow(BoardException.NOT_FOUND::get);

        return new BoardDTO(board);
    }


    //    //게시글 전체 조회
    public Page<BoardDTO> page(BoardPageRequestDTO boardPageRequestDTO) {
        log.info("pageService()---");
        try {
            Sort sort = Sort.by("id").ascending();
            Pageable pageable = boardPageRequestDTO.getPageable(sort);
            return boardRepository.pageDTO(pageable);
        } catch (Exception e) {
            log.error("예외 코드 : " + e.getMessage());
            throw BoardException.NOT_FOUND.get();
        }
    }

    //    //게시글 전체 조회 (검색) -- 현재 멤버 테이블이 없어 참조를 못하여 작성자 검색은 추후 추가 예정
    public Page<BoardDTO> pageSearch(String keyword, BoardPageRequestDTO boardPageRequestDTO) {
        log.info("pageSearchService()---");
        try {
            Sort sort = Sort.by("id").ascending();
            Pageable pageable = boardPageRequestDTO.getPageable(sort);
            return boardRepository.pageSearch(keyword,pageable);
        } catch (Exception e) {
            log.error("예외 코드 : " + e.getMessage());
            throw BoardException.NOT_FOUND.get();
        }
    }

    /**
     * 게시판 수정
     */
    @Transactional
    public BoardEntity update(
            final Long boardId,
            final BoardUpdateRequest request,
            final MultipartFile boardImage
    ) {
        MemberEntity memberEntity = retrieveMember(request.getMemberId());
        BoardEntity boardEntity = retrieveBoard(boardId);

        String imageUrl = (boardImage != null && !boardImage.isEmpty())
                ? imageRepository.upload(boardImage)
                : boardEntity.getImageUrl();

        boardEntity.update(
                memberEntity,
                request.getBoardTitle(),
                request.getBoardContent(),
                request.getCategory(),
                imageUrl
        );
        return boardRepository.save(boardEntity);
    }

    private MemberEntity retrieveMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 유효하지 않은 회원입니다."));
    }

    private BoardEntity retrieveBoard(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 등록되지 않은 게시글입니다."));
    }


}
