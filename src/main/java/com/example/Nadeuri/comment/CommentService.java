package com.example.Nadeuri.comment;

import com.example.Nadeuri.board.BoardEntity;
import com.example.Nadeuri.board.BoardRepository;
import com.example.Nadeuri.member.MemberEntity;
import com.example.Nadeuri.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public CommentService(CommentRepository commentRepository, BoardRepository boardRepository, MemberRepository memberRepository) {
        this.commentRepository = commentRepository;
        this.boardRepository = boardRepository;
        this.memberRepository = memberRepository;
    }

    // 댓글 생성
    @Transactional
    public CommentDTO createComment(CommentDTO commentDTO) {
        BoardEntity board = boardRepository.findById(commentDTO.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("ID가 " + commentDTO.getBoardId() + "인 게시판을 찾을 수 없습니다."));

        MemberEntity member = memberRepository.findById(commentDTO.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("ID가 " + commentDTO.getMemberId() + "인 회원을 찾을 수 없습니다."));

        CommentEntity comment = CommentDTO.toEntity(commentDTO, board, member);
        comment = commentRepository.save(comment);
        return CommentDTO.fromEntity(comment);
    }

    // 게시글 ID로 댓글 조회
    @Transactional(readOnly = true)
    public List<CommentDTO> getCommentsByBoardId(Long boardId) {
        List<CommentEntity> comments = commentRepository.findByBoard_Id(boardId);
        List<CommentDTO> commentDTOs = new ArrayList<>();
        for (CommentEntity comment : comments) {
            CommentDTO dto = CommentDTO.fromEntity(comment);
            commentDTOs.add(dto);
        }
        return commentDTOs;
    }

    // 유저가 작성한 모든 댓글 조회
    @Transactional(readOnly = true)
    public List<CommentDTO> getCommentsByMemberId(Long memberId) {
        List<CommentEntity> comments = commentRepository.findByMember_MemberNo(memberId);
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
                .orElseThrow(() -> new IllegalArgumentException("ID가 " + commentId + "인 댓글을 찾을 수 없습니다."));

        if (!comment.getMember().getMemberNo().equals(memberId)) {
            throw new IllegalArgumentException("이 댓글을 수정할 권한이 없습니다.");
        }

        comment.updateContent(content);

        // 이미 영속성 컨텍스트에 있는 객체이므로 save 필요 없음
        return CommentDTO.fromEntity(comment);
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId, Long memberId) {
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("ID가 " + commentId + "인 댓글을 찾을 수 없습니다."));

        if (!comment.getMember().getMemberNo().equals(memberId)) {
            throw new IllegalArgumentException("이 댓글을 삭제할 권한이 없습니다.");
        }

        commentRepository.delete(comment);
    }
}