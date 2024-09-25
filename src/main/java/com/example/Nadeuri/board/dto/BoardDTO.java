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

    private Long memberId;

    private String boardTitle;

    private String boardContent;

    private Category category;

    private String imageUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    protected LocalDateTime deletedAt;

    public BoardDTO(BoardEntity boardEntity) {
        this.id = boardEntity.getId();
        this.memberId = boardEntity.getMember().getMemberNo();
        this.boardTitle = boardEntity.getBoardTitle();
        this.boardContent = boardEntity.getBoardContent();
        this.category = boardEntity.getCategory();
        this.imageUrl = boardEntity.getImageUrl();
        this.createdAt = boardEntity.getCreatedAt();
        this.updatedAt = boardEntity.getUpdatedAt();
        this.deletedAt = boardEntity.getDeletedAt();
    }

}
