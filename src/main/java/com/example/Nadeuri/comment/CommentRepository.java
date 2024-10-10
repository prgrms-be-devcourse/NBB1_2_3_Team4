package com.example.Nadeuri.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    // 특정 게시글에 달린 모든 댓글 조회
    List<CommentEntity> findByBoardId(Long boardId);

    // 특정 유저의 모든 댓글 조회 - userId
    List<CommentEntity> findByMemberUserId(String userId);

}