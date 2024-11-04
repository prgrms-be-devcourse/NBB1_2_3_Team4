//package com.example.Nadeuri.member.controller;
//
//import com.example.Nadeuri.member.dto.MemberDTO;
//import com.example.Nadeuri.member.security.util.JWTUtil;
//import com.example.Nadeuri.member.MemberService;
//import io.jsonwebtoken.ExpiredJwtException;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/v1/token")
//@Log4j2
//public class TokenController {
//    private final MemberService memberService;
//    private final JWTUtil jwtUtil;
//
//    //POST로 /api/v1/token/make 요청을 처리하는 makeToken 메서드
//    @PostMapping("/make")
//    public ResponseEntity<Map<String,Object>> makeToken(@RequestBody MemberDTO memberDTO) {        //매개변수로 MemberDTO 객체를 전달받아
//        log.info("makeToken() -----------");
//
//        //사용자 정보 가져오기
//        MemberDTO foundMemberDTO = memberService.read(memberDTO.getUserId(), memberDTO.getPassword());  //데이터베이스에 해당 객체 정보가 존재하는지 확인한 후
//        log.info("--- foundMemberDTO : " + foundMemberDTO);//데이터베이스의 데이터를 넘겨받아서 로그 확인
//
//        //토큰 생성
//        Map<String, Object> payloadMap = foundMemberDTO.getPayload();
//        String accessToken = jwtUtil.createToken(payloadMap, 60);    //60분 유효
//        String refreshToken = jwtUtil.createToken(Map.of("userId", foundMemberDTO.getUserId()),
//                60 * 24 * 7);                   //7일 유효
//
//        log.info("--- accessToken : " + accessToken);
//        log.info("--- refreshToken : " + refreshToken);
//
//        return ResponseEntity.ok(Map.of("accessToken", accessToken, "refreshToken", refreshToken));
//
//    }
//
//    //리프레시 토큰 발행 시 - 예외 상황 코드와 메시지 전송
//    public ResponseEntity<Map<String ,String>> handleException(String message, int status) {
//        return ResponseEntity.status(status)   //1. 액세스 토큰이 없는 경우      400
//                .body(Map.of("error", message));  //2. 리프레시 토큰이 없는 경우    400
//        //3. mid가 없는 경우            400
//        //4. 리프레시 토큰이 만료된 경우   400
//        //5. 그 외의 예외               400
//    }
//
//    //리프레시 토큰 검증 및 액세스토큰 만료시 토큰 재발급 ------------------------------------------------------
//    @PostMapping("/refresh")
//    public ResponseEntity<Map<String,String>> refreshToken(  //쿼리 파라미터에 발급받은 refresh값과 userId 입력해서 요청
//            @RequestHeader("Authorization") String headerAuth,
//            @RequestParam("refreshToken") String refreshToken,
//            @RequestParam("userId") String userId) {
//        //파라미터 값 전달 확인 - 값이 없으면 400 Bad Request 반환
//        log.info("--- refreshToken()");
//        log.info("--- Authorization : " + headerAuth);
//        log.info("--- refreshToken : " + refreshToken);
//        log.info("--- userId : " + userId);
//        if(headerAuth == null || !headerAuth.startsWith("Bearer ")) {
//            //액세스 토큰이 없는 경우
//            return handleException("액세스 토큰이 없습니다.", 400);
//        }
//
//        if(refreshToken == null || refreshToken.isEmpty()) { //리프레시 토큰이 없는 경우
//            return handleException("리프레시 토큰이 없습니다.", 400);
//        }
//
//        if(userId == null || userId.isEmpty()) { //아이디가 없는 경우
//            return handleException("아이디가 없습니다.", 400);
//        }
//
//        try {              //1. 액세스 토큰 만료 여부 확인
//            String accessToken = headerAuth.substring(7);  //"Bearer " 를 제외하고 토큰값 저장
//            Map<String, Object> claims = jwtUtil.validateToken(accessToken);  //리프레시 토큰 유효성 검증
//            log.info("--- 토큰 유효성 검증 완료 ---");
//
//            //1.1 액세스 토큰 유효성 검증
//            //1.2 만료 기간이 남은 경우
//        } catch(ExpiredJwtException e) { //2. 만료 기간이 지난 경우
//            log.info("--- 액세스 토큰 만료 기간 경과");
//
//            try {
//                //새로운 토큰 생성 메서드 호출
//                return ResponseEntity.ok(makeNewToken(userId, refreshToken));
//            } catch(ExpiredJwtException ee) {
//                log.info("--- 리프레시 토큰 만료 기간 경과");
//                return handleException("리프레시 토큰 기간 만료 : " + ee.getMessage(), 400);
//            }
//
//        } catch(Exception e) { //3. 그 외의 예외
//            e.printStackTrace();
//            return handleException("리프레시 토큰 검증 예외 : " + e.getMessage(), 400);
//        }
//        return null;
//    }
//
//    //새로운 토큰 생성
//    public Map<String, String> makeNewToken(String userId, String refreshToken) {
//        Map<String, Object> claims = jwtUtil.validateToken(refreshToken);  //리프레시 토큰 유효성 검증
//        log.info("--- 리프레시 토큰 유효성 검증 완료 ---");
//
//        if(!claims.get("userId").equals(userId)) {  //claims의 userId와 매개변수로 전달받은 userId가 일치하지 않는 경우
//            throw new RuntimeException("INVALID REFRESH TOKEN userId");  //RuntimeException 발생 시키기 - INVALID REFRESH userId
//        }
//        log.info("--- make new token ---");
//        MemberDTO foundMemberDTO = memberService.read(userId);
//        Map<String, Object> payloadMap = foundMemberDTO.getPayload();
//        String newAccessToken = jwtUtil.createToken(payloadMap, 1);  //1분 유효
//        String newRefreshToken = jwtUtil.createToken(Map.of("userId", userId), 2); //2분 유효
//
//
//        //액세스 토큰과 리프레시 토큰 생성하여 mid와 반환
//        return Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken, "userId", userId);
//    }//END makeToken()
//
//    //상태 코드 400과 메시지 전송
//    public ResponseEntity<Map<String, String>> sendResponse(String message) {
//        return new ResponseEntity<>(Map.of("error", message), HttpStatus.BAD_REQUEST);
//    }
//
//    //리프레시 토큰 검증 처리
//    @PostMapping("/refreshVerify")
//    public ResponseEntity<Map<String,String>> refreshVerify(
//            @RequestHeader("Authorization") String headerAuth,
//            @RequestParam("refreshToken")   String refreshToken,
//            @RequestParam("userId") String userId) {
//        //1. 파라미터 값 확인 - 값이 없으면 메시지를 전달하여 400 BAD_REQUEST 반환
//        if(headerAuth == null || !headerAuth.startsWith("Bearer ")) return sendResponse("액세스 토큰이 없습니다.");
//        if(refreshToken.isEmpty()) return sendResponse("리프레시 토큰이 없습니다.");
//        if(userId.isEmpty())   return sendResponse("아이디가 없습니다.");
//
//        try { //2. 액세스 유효성 검증
//            String accessToken = headerAuth.substring(7);
//            Map<String, Object> claims = jwtUtil.validateToken(accessToken);
//            log.info("--- 1.액세스 토큰 유효");
//
//            return ResponseEntity.ok(Map.of("accessToken", accessToken, "refreshToken", refreshToken, "userId", userId));
//
//        } catch(ExpiredJwtException e) {
//            log.info("--- 2.액세스 토큰 만료기간 경과");
//
//            try { //3. 리프레시 토큰 유효성 검증
//                Map<String, Object> claims = jwtUtil.validateToken(refreshToken);
//                log.info("--- 3.리프레시 토큰 유효");
//
//                if(!claims.get("userId").equals(userId)) { //userId가 일치하지 않는 경우
//                    return sendResponse("INVALID REFRESH TOKEN userId");
//                }
//
//                log.info("--- 4.새로운 토큰 생성");
//                MemberDTO foundMemberDTO = memberService.read(userId);
//                Map<String, Object> payloadMap = foundMemberDTO.getPayload();
//                String newAccessToken = jwtUtil.createToken(payloadMap, 10);  //1분 유효
//                String newRefreshToken = jwtUtil.createToken(Map.of("userId", userId), 3); //3분 유효
//
//                //신규 생성 토큰들과 mid 반환
//                return ResponseEntity.ok(Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken, "userId", userId));
//            }catch(ExpiredJwtException ee) {
//                log.info("--- 5.리프레시 토큰 만료기간 경과");
//                return sendResponse("리프레시 토큰 만료기간 경과" + ee.getMessage());
//            }
//        } catch(Exception e) {
//            log.info("--- 리프레시 토큰 처리 기타 예외");
//            return sendResponse("리프레시 토큰 처리 예외 : " + e.getMessage());
//        }
//
//    }
//
//}


