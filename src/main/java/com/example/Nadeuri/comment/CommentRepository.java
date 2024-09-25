package com.example.Nadeuri.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByBoardId(Long boardId);        // 게시글에 달려있는 모든댓글 조회

    List<CommentEntity> findByMemberId(Long memberId);      // 유저가 작성한 모든댓글 조회
}