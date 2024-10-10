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

class CommentControllerTest {

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
        String memberId = "testUser";
        CommentDTO commentDTO = CommentDTO.builder()
                .boardId(boardId)
                .memberId(memberId)
                .content("테스트 댓글")
                .build();

        BoardEntity board = BoardEntity.builder()
                .id(boardId)
                .build();

        MemberEntity member = MemberEntity.builder()
                .userId(memberId)
                .build();

        CommentEntity commentEntity = CommentEntity.builder()
                .id(1L)
                .board(board)
                .member(member)
                .content("테스트 댓글")
                .build();

        when(boardRepository.findById(boardId)).thenReturn(Optional.of(board));
        when(memberRepository.findByUserId(memberId)).thenReturn(Optional.of(member));
        when(commentRepository.save(any(CommentEntity.class))).thenReturn(commentEntity);

        CommentDTO createdComment = commentService.createComment(commentDTO);

        assertNotNull(createdComment);
        assertEquals("테스트 댓글", createdComment.getContent());
        verify(commentRepository, times(1)).save(any(CommentEntity.class));
    }

    @Test
    void 댓글_조회_테스트() {
        Long boardId = 1L;

        BoardEntity board = BoardEntity.builder()
                .id(boardId)
                .build();

        MemberEntity member = MemberEntity.builder()
                .userId("testUser")
                .build();

        CommentEntity comment1 = CommentEntity.builder()
                .id(1L)
                .board(board)
                .member(member)
                .content("첫 번째 댓글")
                .build();

        when(commentRepository.findByBoardId(boardId)).thenReturn(Arrays.asList(comment1));

        List<CommentDTO> comments = commentService.readBoardId(boardId);

        assertEquals(1, comments.size());
        assertEquals("첫 번째 댓글", comments.get(0).getContent());
        verify(commentRepository, times(1)).findByBoardId(boardId);
    }

    @Test
    void 댓글_수정_테스트() {
        Long commentId = 1L;
        String memberId = "testUser";
        String updatedContent = "수정된 댓글";

        MemberEntity member = MemberEntity.builder()
                .userId(memberId)
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
        String memberId = "testUser";

        MemberEntity member = MemberEntity.builder()
                .userId(memberId)
                .build();

        CommentEntity comment = CommentEntity.builder()
                .id(commentId)
                .member(member)
                .build();

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        commentService.deleteComment(commentId, memberId);

        verify(commentRepository, times(1)).delete(comment);
    }

    @Test
    void 답글_생성_테스트() {
        Long boardId = 1L;
        Long parentCommentId = 1L;  // 부모 댓글 ID
        String memberId = "testUser";

        CommentDTO replyDTO = CommentDTO.builder()
                .boardId(boardId)
                .memberId(memberId)
                .content("답글 내용")
                .parentCommentId(parentCommentId)  // 부모 댓글 설정
                .build();

        BoardEntity board = BoardEntity.builder()
                .id(boardId)
                .build();

        MemberEntity member = MemberEntity.builder()
                .userId(memberId)
                .build();

        CommentEntity parentComment = CommentEntity.builder()
                .id(parentCommentId)
                .board(board)
                .member(member)
                .content("부모 댓글")
                .build();

        CommentEntity replyComment = CommentEntity.builder()
                .id(2L)
                .board(board)
                .member(member)
                .parentComment(parentComment)
                .content("답글 내용")
                .build();

        when(boardRepository.findById(boardId)).thenReturn(Optional.of(board));
        when(memberRepository.findByUserId(memberId)).thenReturn(Optional.of(member));
        when(commentRepository.findById(parentCommentId)).thenReturn(Optional.of(parentComment));
        when(commentRepository.save(any(CommentEntity.class))).thenReturn(replyComment);

        CommentDTO createdReply = commentService.createComment(replyDTO);

        assertNotNull(createdReply);
        assertEquals("답글 내용", createdReply.getContent());
        assertEquals(parentCommentId, createdReply.getParentCommentId());
        verify(commentRepository, times(1)).save(any(CommentEntity.class));
    }

    @Test
    void 답글_조회_테스트() {
        Long boardId = 1L;
        Long parentCommentId = 1L;

        BoardEntity board = BoardEntity.builder()
                .id(boardId)
                .build();

        MemberEntity member = MemberEntity.builder()
                .userId("testUser")
                .build();

        // 부모 댓글 엔티티
        CommentEntity parentComment = CommentEntity.builder()
                .id(parentCommentId)
                .board(board)
                .member(member)
                .content("부모 댓글")
                .build();

        // 답글 엔티티
        CommentEntity replyComment = CommentEntity.builder()
                .id(2L)
                .board(board)
                .member(member)
                .parentComment(parentComment)
                .content("답글 내용")
                .build();

        // 부모 댓글에 답글 리스트 설정
        parentComment = CommentEntity.builder()
                .id(parentComment.getId())
                .board(board)
                .member(member)
                .content(parentComment.getContent())
                .replies(Arrays.asList(replyComment))  // 답글 리스트 추가
                .build();

        // Mock 설정 - 부모 댓글과 답글을 반환
        when(commentRepository.findByBoardId(boardId)).thenReturn(Arrays.asList(parentComment));

        // 댓글 및 답글 조회
        List<CommentDTO> comments = commentService.readBoardId(boardId);

        assertEquals(1, comments.size());  // 부모 댓글 개수 확인
        assertEquals("부모 댓글", comments.get(0).getContent());
        assertEquals(1, comments.get(0).getReplies().size());  // 답글 개수 확인
        assertEquals("답글 내용", comments.get(0).getReplies().get(0).getContent());

        verify(commentRepository, times(1)).findByBoardId(boardId);
    }

    @Test
    void 답글_수정_테스트() {
        Long replyId = 2L;
        String memberId = "testUser";
        String updatedContent = "수정된 답글";

        MemberEntity member = MemberEntity.builder()
                .userId(memberId)
                .build();

        BoardEntity board = BoardEntity.builder()
                .id(1L)
                .build();

        CommentEntity parentComment = CommentEntity.builder()
                .id(1L)
                .board(board)
                .member(member)
                .content("부모 댓글")
                .build();

        CommentEntity replyComment = CommentEntity.builder()
                .id(replyId)
                .parentComment(parentComment)
                .board(board)
                .member(member)
                .content("이전 답글 내용")
                .build();

        when(commentRepository.findById(replyId)).thenReturn(Optional.of(replyComment));

        CommentDTO updatedReply = commentService.updateComment(replyId, updatedContent, memberId);

        assertNotNull(updatedReply);
        assertEquals(updatedContent, updatedReply.getContent());
        verify(commentRepository, times(1)).findById(replyId);
    }

    @Test
    void 답글_삭제_테스트() {
        Long replyId = 2L;
        String memberId = "testUser";

        MemberEntity member = MemberEntity.builder()
                .userId(memberId)
                .build();

        CommentEntity replyComment = CommentEntity.builder()
                .id(replyId)
                .member(member)
                .build();

        when(commentRepository.findById(replyId)).thenReturn(Optional.of(replyComment));

        commentService.deleteComment(replyId, memberId);

        verify(commentRepository, times(1)).delete(replyComment);
    }
}
