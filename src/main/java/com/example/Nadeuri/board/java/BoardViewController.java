package com.example.Nadeuri.board.java;

//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/boards")
//public class BoardViewController {

//    private final BoardService2 boardService2;
//    private final MemberService memberService;

//    /**
//     * 게시글 목록 페이지
//     */
//    @GetMapping("/list")
//    public String listBoardPage(@Validated BoardPageRequestDTO boardPageRequestDTO, Model model) {
//        // 페이지 요청에 따라 게시글 목록을 가져옴
//        Page<BoardDTO> boards = boardService2.page(boardPageRequestDTO);
//        model.addAttribute("boards", boards.getContent());
//        model.addAttribute("page", boards);
//        return "board/list"; // list.html로 이동
//    }
//
//    //게시글 상세 조회
//    @GetMapping("/{id}")
//    public String read(@PathVariable("id") Long boardId,
//                       Model model) {
//        BoardDTO board = boardService2.read(boardId); // 게시글 조회
//        if (board != null) {
//            model.addAttribute("article", board); // 모델에 게시글 추가
//        } else {
//            model.addAttribute("error", "게시글을 찾을 수 없습니다."); // 게시글이 없을 경우 에러 메시지 추가
//        }
//
//        return "board/article"; // article.html로 이동
//    }
//
////    @GetMapping("/{id}")
////    public ResponseEntity<ApiResponse> read(@PathVariable("id") Long boardId,
////                                            Authentication authentication) {
////        BoardDTO board = boardService.read(boardId); // 게시글 조회
////        return ResponseEntity.ok(ApiResponse.success(board));
////    }
//
//    @GetMapping("/create")
//    public String createForm() {
//        return "board/create"; // 게시글 작성 HTML 파일 이름
//    }
//
//    // 게시글 작성 처리
//    @PostMapping("/create")
//    public String createBoard(@ModelAttribute BoardCreateRequest request,
//                              @RequestParam MultipartFile image,
//                              Authentication authentication) {
//        // Authentication 객체에서 username 추출
//        String userId = authentication.getName(); // username 가져오기
//        Long memberId = memberService.findByUserId(userId).getMemberNo(); // username으로 memberId 조회
//        BoardCreateRequest updatedRequest = new BoardCreateRequest(
//                memberId,
//                request.getBoardTitle(),
//                request.getBoardContent(),
//                request.getCategory()
//        );
//
//        // 게시글 등록 처리
//        boardService2.register(updatedRequest, image);
//        return "redirect:/boards/list"; // 게시글 목록 페이지로 리다이렉트
//    }
//

//}