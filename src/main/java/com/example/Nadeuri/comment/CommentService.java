package com.example.Nadeuri.comment;

import com.example.Nadeuri.board.BoardEntity;
import com.example.Nadeuri.board.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public CommentService(CommentRepository commentRepository, BoardRepository boardRepository) {
        this.commentRepository = commentRepository;
        this.boardRepository = boardRepository;
    }

    // 댓글 생성
    @Transactional
    public CommentDTO createComment(CommentDTO commentDTO) {
        BoardEntity board = boardRepository.findById(commentDTO.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("게시판을 찾을 수 없습니다."));

        CommentEntity commentEntity = CommentDTO.toEntity(commentDTO, board);
        commentEntity = commentRepository.save(commentEntity);
        return CommentDTO.fromEntity(commentEntity);
    }

    // 게시글 ID로 댓글 조회
    @Transactional(readOnly = true)
    public List<CommentDTO> getCommentsByBoardId(Long boardId) {
        List<CommentEntity> comments = commentRepository.findByBoardId(boardId);
        List<CommentDTO> commentDTOs = new ArrayList<>();
        for (CommentEntity comment : comments) {
            CommentDTO dto = CommentDTO.fromEntity(comment);
            commentDTOs.add(dto);
        }
        return commentDTOs;
    }

    // 댓글 수정
    @Transactional
    public CommentDTO updateComment(Long commentId, String content, Long memberId) {
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        if (!comment.getMemberId().equals(memberId)) {
            throw new IllegalArgumentException("이 댓글을 수정할 권한이 없습니다.");
        }

        comment.setContent(content);
        comment = commentRepository.save(comment);
        return CommentDTO.fromEntity(comment);
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId, Long memberId) {
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        if (!comment.getMemberId().equals(memberId)) {
            throw new IllegalArgumentException("이 댓글을 삭제할 권한이 없습니다.");
        }

        commentRepository.delete(comment);
    }


}