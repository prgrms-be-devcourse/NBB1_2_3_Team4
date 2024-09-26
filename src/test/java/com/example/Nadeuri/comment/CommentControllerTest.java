package com.example.Nadeuri.comment;

import com.example.Nadeuri.board.BoardEntity;
import com.example.Nadeuri.board.BoardRepository;
import com.example.Nadeuri.member.MemberEntity;
import com.example.Nadeuri.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 댓글_생성_테스트() {
        Long boardId = 1L;
        Long memberId = 1L;
        CommentDTO commentDTO = CommentDTO.builder()
                .boardId(boardId)
                .memberId(memberId)
                .content("테스트 댓글")
                .build();

        BoardEntity board = BoardEntity.builder()
                .id(boardId)
                .build();

        MemberEntity member = MemberEntity.builder()
                .memberNo(memberId)
                .build();

        CommentEntity commentEntity = CommentEntity.builder()
                .id(1L)
                .board(board)
                .member(member)
                .content("테스트 댓글")
                .build();

        when(boardRepository.findById(boardId)).thenReturn(Optional.of(board));
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(commentRepository.save(any(CommentEntity.class))).thenReturn(commentEntity);

        CommentDTO createdComment = commentService.createComment(commentDTO);

        assertNotNull(createdComment);
        assertEquals("테스트 댓글", createdComment.getContent());
        verify(commentRepository, times(1)).save(any(CommentEntity.class));
    }

    @Test
    void 게시글에_달린_댓글_조회_테스트() {
        Long boardId = 1L;

        BoardEntity board = BoardEntity.builder()
                .id(boardId)
                .build();

        MemberEntity member = MemberEntity.builder()
                .memberNo(1L)
                .build();

        CommentEntity comment1 = CommentEntity.builder()
                .id(1L)
                .board(board)
                .member(member)
                .content("첫 번째 댓글")
                .build();

        CommentEntity comment2 = CommentEntity.builder()
                .id(2L)
                .board(board)
                .member(member)
                .content("두 번째 댓글")
                .build();

        when(commentRepository.findByBoard_Id(boardId)).thenReturn(Arrays.asList(comment1, comment2));

        List<CommentDTO> comments = commentService.getCommentsByBoardId(boardId);

        assertEquals(2, comments.size());
        assertEquals("첫 번째 댓글", comments.get(0).getContent());
        assertEquals("두 번째 댓글", comments.get(1).getContent());
        verify(commentRepository, times(1)).findByBoard_Id(boardId);
    }

    @Test
    void 유저가_작성한_댓글_조회_테스트() {
        Long memberId = 1L;

        MemberEntity member = MemberEntity.builder()
                .memberNo(memberId)
                .build();

        BoardEntity board = BoardEntity.builder()
                .id(1L)
                .build();

        CommentEntity comment1 = CommentEntity.builder()
                .id(1L)
                .member(member)
                .board(board)
                .content("유저의 첫 번째 댓글")
                .build();

        CommentEntity comment2 = CommentEntity.builder()
                .id(2L)
                .member(member)
                .board(board)
                .content("유저의 두 번째 댓글")
                .build();

        when(commentRepository.findByMember_MemberNo(memberId)).thenReturn(Arrays.asList(comment1, comment2));

        List<CommentDTO> comments = commentService.getCommentsByMemberId(memberId);

        assertEquals(2, comments.size());
        assertEquals("유저의 첫 번째 댓글", comments.get(0).getContent());
        assertEquals("유저의 두 번째 댓글", comments.get(1).getContent());
        verify(commentRepository, times(1)).findByMember_MemberNo(memberId);
    }

    @Test
    void 댓글_수정_테스트() {
        Long commentId = 1L;
        Long memberId = 1L;
        String updatedContent = "수정된 댓글";

        MemberEntity member = MemberEntity.builder()
                .memberNo(memberId)
                .build();

        BoardEntity board = BoardEntity.builder()
                .id(1L)
                .build();

        CommentEntity comment = CommentEntity.builder()
                .id(commentId)
                .member(member)
                .board(board)
                .content("이전 댓글")
                .build();

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        CommentDTO updatedComment = commentService.updateComment(commentId, updatedContent, memberId);

        assertNotNull(updatedComment);
        assertEquals(updatedContent, updatedComment.getContent());
        verify(commentRepository, times(1)).findById(commentId);
    }

    @Test
    void 댓글_삭제_테스트() {
        Long commentId = 1L;
        Long memberId = 1L;

        MemberEntity member = MemberEntity.builder()
                .memberNo(memberId)
                .build();

        CommentEntity comment = CommentEntity.builder()
                .id(commentId)
                .member(member)
                .build();

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        commentService.deleteComment(commentId, memberId);

        verify(commentRepository, times(1)).delete(comment);
    }
}