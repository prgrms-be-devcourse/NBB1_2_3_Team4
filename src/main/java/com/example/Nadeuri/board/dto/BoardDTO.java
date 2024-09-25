package com.example.Nadeuri.board.dto;

import com.example.Nadeuri.board.BoardEntity;
import com.example.Nadeuri.board.Category;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BoardDTO {

    private Long id;

    private Long memberId; //멤버 객체로 변경 필요

    private String boardTitle;

    private String boardContent;

    private Category category;

    private String imageUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    protected LocalDateTime deletedAt;

    public BoardDTO(BoardEntity boardEntity) {
        this.id = boardEntity.getId();
        this.memberId = boardEntity.getMemberId();
        this.boardTitle = boardEntity.getBoardTitle();
        this.boardContent = boardEntity.getBoardContent();
        this.category = boardEntity.getCategory();
        this.imageUrl = boardEntity.getImageUrl();
        this.createdAt = boardEntity.getCreatedAt();
        this.updatedAt = boardEntity.getUpdatedAt();
        this.deletedAt = boardEntity.getDeletedAt();
    }

    public BoardEntity toEntity(BoardDTO boardDTO){
        BoardEntity boardEntity = BoardEntity.builder()
                .id(id)
                .memberId(memberId)
                .boardTitle(boardTitle)
                .boardContent(boardContent)
                .category(category)
                .imageUrl(imageUrl)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .deletedAt(deletedAt)
                .build();

        return boardEntity;
    }
}
