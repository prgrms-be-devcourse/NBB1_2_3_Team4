package com.example.Nadeuri.board.dto.response;

import com.example.Nadeuri.board.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BoardDeleteResponse {
    private final Long boardId;

    public static BoardDeleteResponse from(final BoardEntity board) {
        return new BoardDeleteResponse(board.getId());
    }
}
