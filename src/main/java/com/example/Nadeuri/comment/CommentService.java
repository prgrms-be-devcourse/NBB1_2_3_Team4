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
                .orElseThrow(() -> new IllegalArgumentException("게시판을 찾을 수 없습니다. ID: " + commentDTO.getBoardId()));

        MemberEntity member = memberRepository.findById(commentDTO.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다. ID: " + commentDTO.getMemberId()));

        CommentEntity comment = CommentDTO.toEntity(commentDTO, board, member);
        comment = commentRepository.save(comment);

        return CommentDTO.fromEntity(comment);
    }

    // 게시글 ID로 댓글 조회
    @Transactional(readOnly = true)
    public List<CommentDTO> readBoardId(Long boardId) {
        List<CommentEntity> comments = commentRepository.findByBoard_Id(boardId);
        List<CommentDTO> commentDTOs = new ArrayList<>();

        for (CommentEntity comment : comments) {
            commentDTOs.add(CommentDTO.fromEntity(comment));
        }

        return commentDTOs;
    }

    // 유저가 작성한 모든 댓글 조회
    @Transactional(readOnly = true)
    public List<CommentDTO> readMemberId(Long memberId) {
        List<CommentEntity> comments = commentRepository.findByMember_MemberNo(memberId);
        List<CommentDTO> commentDTOs = new ArrayList<>();

        for (CommentEntity comment : comments) {
            commentDTOs.add(CommentDTO.fromEntity(comment));
        }

        return commentDTOs;
    }

    // 댓글 수정
    @Transactional
    public CommentDTO updateComment(Long commentId, String content, Long memberId) {
        CommentEntity comment = check(commentId, memberId);
        comment.updateContent(content);

        return CommentDTO.fromEntity(comment);
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId, Long memberId) {
        CommentEntity comment = check(commentId, memberId);
        commentRepository.delete(comment);
    }

    // 댓글 작성자 검증하는 메서드 - Controller
    public boolean checkOwner(Long commentId, String username) {
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다. ID: " + commentId));
        return comment.getMember().getMemberNo().toString().equals(username);
    }

    // 댓글 작성자 검증하는 메서드 - Service
    private CommentEntity check(Long commentId, Long memberId) {
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다. ID: " + commentId));

        if (!comment.getMember().getMemberNo().equals(memberId)) {
            throw new IllegalArgumentException("이 댓글에 대한 권한이 없습니다. ID: " + commentId);
        }

        return comment;
    }
}