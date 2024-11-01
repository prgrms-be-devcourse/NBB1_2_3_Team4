//package com.example.Nadeuri.board.dto.request;
//
//import com.example.Nadeuri.board.Category;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import lombok.Getter;
//
//@Getter
//public class BoardCreateRequest {
//
//    @NotNull(message = "회원만 게시글을 작성할 수 있습니다.")
//    private final Long memberId;
//
//    @NotBlank(message = "제목을 확인해주세요. 빈값 혹은 null 일 수 없습니다.")
//    private final String boardTitle;
//
//    @NotBlank(message = "내용을 확인해주세요. 빈값 혹은 null 일 수 없습니다.")
//    private final String boardContent;
//
//    @NotNull(message = "카테고리 선택은 필수입니다.")
//    private final Category category;
//
//    public BoardCreateRequest(
//            final Long memberId,
//            final String boardTitle,
//            final String boardContent,
//            final Category category
//    ) {
//        this.memberId = memberId;
//        this.boardTitle = boardTitle;
//        this.boardContent = boardContent;
//        this.category = category;
//    }
//
//}
