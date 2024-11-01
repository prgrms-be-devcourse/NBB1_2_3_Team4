//package com.example.Nadeuri.likes;
//
//import com.example.Nadeuri.board.BoardEntity;
//import com.example.Nadeuri.board.BoardRepository;
//import com.example.Nadeuri.member.MemberEntity;
//import com.example.Nadeuri.member.MemberRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import java.util.Optional;
//
//@Transactional(readOnly = true)
//@RequiredArgsConstructor
//@Service
//public class LikeService {
//    private final LikeRepository likeRepository;
//    private final BoardRepository boardRepository;
//    private final MemberRepository memberRepository;
//
//    /**
//     * 좋아요 등록
//     */
////    @Transactional
////    public void like(final Long boardId, final String userId) {
////        BoardEntity board = retrieveBoard(boardId);
////        MemberEntity member = retrieveMember(userId);
////
////        handleLike(member, board);
////        board.increaseLikeCount();
////    }
//
//    /**
//     * 좋아요 취소
//     */
////    @Transactional
////    public LikeEntity unLike(final Long boardId, final String userId) {
////        BoardEntity board = retrieveBoard(boardId);
////        MemberEntity member = retrieveMember(userId);
////
////        return findLikeByMemberAndBoard(member, board)
////                .map(like -> {
////                    like.setLikeDeleted(true);
////                    board.decreaseLikeCount();
////                    return like;
////                })
////                .orElseThrow(LikeException.LIKE_NOT_EXIST::get);
////    }
////
////    private BoardEntity retrieveBoard(Long boardId) {
////        return boardRepository.findById(boardId)
////                .orElseThrow(BoardException.NOT_FOUND::get);
////    }
//
////    private MemberEntity retrieveMember(String userId) {
////        return memberRepository.findByUserId(userId)
////                .orElseThrow(MemberException.NOT_FOUND::get);
////    }
//    private void handleLike(MemberEntity member, BoardEntity board) {
//        Optional<LikeEntity> existingLike = findLikeByMemberAndBoard(member, board);
//
//        if (existingLike.isPresent()) {
//            restoreLike(existingLike.get());
//        } else {
//            createNewLike(member, board);
//        }
//    }
//
//    private void restoreLike(LikeEntity like) {
//        if (like.isDeleted()) {
//            like.setLikeDeleted(false);
//            likeRepository.save(like);
//        }
//    }
//
//    private void createNewLike(MemberEntity member, BoardEntity board) {
//        LikeEntity newLike = LikeEntity.create(member, board);
//        likeRepository.save(newLike);
//    }
//
//    private Optional<LikeEntity> findLikeByMemberAndBoard(MemberEntity member, BoardEntity board) {
//        return likeRepository.findByMemberAndBoard(member, board);
//    }
//}
