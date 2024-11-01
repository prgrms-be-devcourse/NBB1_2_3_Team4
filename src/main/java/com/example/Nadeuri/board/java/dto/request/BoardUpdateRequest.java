//package com.example.Nadeuri.board.dto.request;
//
//import com.example.Nadeuri.board.Category;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import lombok.Getter;
//
//@Getter
//public class BoardUpdateRequest {
//    // TODO: 인증 도입 시 삭제 필요
//    @NotNull(message = "회원만 게시글을 작성할 수 있습니다.")
//    private Long memberId;
//
//    @NotBlank(message = "제목을 확인해주세요. 빈값 혹은 null 일 수 없습니다.")
//    private String boardTitle;
//
//    @NotBlank(message = "내용을 확인해주세요. 빈값 혹은 null 일 수 없습니다.")
//    private String boardContent;
//
//    @NotNull(message = "카테고리 선택은 필수입니다.")
//    private Category category;
//}
