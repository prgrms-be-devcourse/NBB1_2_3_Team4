package com.example.Nadeuri.board;

import lombok.Getter;

@Getter
public enum Category {
    FOOD("먹거리"),
    PLAY("놀거리");

    private final String description;

    Category(final String description) {
        this.description = description;
    }
}
