package com.example.Nadeuri.likes;

import com.example.Nadeuri.board.BoardEntity;
import com.example.Nadeuri.board.BoardRepository;
import com.example.Nadeuri.board.exception.BoardException;
import com.example.Nadeuri.member.MemberEntity;
import com.example.Nadeuri.member.MemberRepository;
import com.example.Nadeuri.member.exception.MemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    /**
     * 좋아요 등록
     */
    @Transactional
    public void like(final Long boardId, final String userId) {
        BoardEntity board = retrieveBoard(boardId);
        MemberEntity member = retrieveMember(userId);

        if (!isMemberLikedBoard(member, board)) {
            board.increaseLikeCount();
            likeRepository.save(LikeEntity.create(member, board));
        }
    }

    private BoardEntity retrieveBoard(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(BoardException.NOT_FOUND::get);
    }

    private MemberEntity retrieveMember(String userId) {
        return memberRepository.findByUserId(userId)
                .orElseThrow(MemberException.NOT_FOUND::get);
    }

    private boolean isMemberLikedBoard(MemberEntity member, BoardEntity board) {
        return likeRepository.findByMemberAndBoard(member, board).isPresent();
    }

}
