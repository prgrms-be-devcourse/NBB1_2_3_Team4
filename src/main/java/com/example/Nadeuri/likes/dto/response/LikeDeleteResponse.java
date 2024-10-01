package com.example.Nadeuri.likes.dto.response;

import com.example.Nadeuri.likes.LikeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LikeDeleteResponse {
    private final Long likeId;

    public static LikeDeleteResponse from(final LikeEntity like) {
        return new LikeDeleteResponse(like.getId());
    }
}
