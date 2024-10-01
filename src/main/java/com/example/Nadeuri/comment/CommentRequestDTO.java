package com.example.Nadeuri.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDTO {

    @NotNull(message = "게시글 ID는 필수입니다.")
    private Long boardId;

    @NotNull(message = "회원 ID는 필수입니다.")
    private Long memberId;

    @NotBlank(message = "댓글 내용은 필수입니다.")
    private String content;

    @Builder
    public CommentRequestDTO(Long boardId, Long memberId, String content) {
        this.boardId = boardId;
        this.memberId = memberId;
        this.content = content;
    }
}