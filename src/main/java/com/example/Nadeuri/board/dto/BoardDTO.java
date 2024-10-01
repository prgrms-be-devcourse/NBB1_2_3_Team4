package com.example.Nadeuri.board.dto;

import com.example.Nadeuri.board.BoardEntity;
import com.example.Nadeuri.board.Category;
import com.example.Nadeuri.comment.CommentEntity;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter // data에서 getter로 수정
@ToString //테스트 목적으로 추가
@NoArgsConstructor
public class BoardDTO {

    private Long id;

    private String member; // 작성자

    private String boardTitle;

    private String boardContent;

    private Category category;

    private String imageUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    protected LocalDateTime deletedAt;

    private List<String> comments; //댓글

    public BoardDTO(BoardEntity boardEntity) {
        this.id = boardEntity.getId();
        this.member = boardEntity.getMember().getUserId();
        this.boardTitle = boardEntity.getBoardTitle();
        this.boardContent = boardEntity.getBoardContent();
        this.category = boardEntity.getCategory();
        this.imageUrl = boardEntity.getImageUrl();
        this.createdAt = boardEntity.getCreatedAt();
        this.updatedAt = boardEntity.getUpdatedAt();
        this.deletedAt = boardEntity.getDeletedAt();
        this.comments = boardEntity.getComments().stream()
                .map(CommentEntity::getContent)
                .collect(Collectors.toList());
    }
}
