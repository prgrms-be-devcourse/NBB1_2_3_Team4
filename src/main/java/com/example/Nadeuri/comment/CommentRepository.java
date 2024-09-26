package com.example.Nadeuri.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    // 한개의 게시글에 달린 모든 댓글 조회
    List<CommentEntity> findByBoard_Id(Long boardId);

    // 한명의 유저가 작성한 모든 댓글 조회
    List<CommentEntity> findByMember_MemberNo(Long memberId);
}