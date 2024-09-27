package com.example.Nadeuri.member.admin;

import com.example.Nadeuri.common.response.ApiResponse;
import com.example.Nadeuri.member.MemberEntity;
import com.example.Nadeuri.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/v1/admin")
public class AdminController {
    private final AdminService adminService;

    //멤버 전체 조회
    @GetMapping
    public ResponseEntity<ApiResponse> selectAllMember(){
        return ResponseEntity.ok(ApiResponse.success(adminService.getAdminMemberAll()));
    }

    //멤버 조회
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> selectMember(@PathVariable String userId){
        return ResponseEntity.ok(ApiResponse.success(adminService.getAdminMember(userId)));

    }

    //멤버 삭제
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteMember(@PathVariable String userId){
        adminService.deleteAdminMember(userId);

        return ResponseEntity.ok(ApiResponse.success("계정 삭제 완료"));
    }

    //멤버 수정
    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse> updateMember(@PathVariable String userId,
                                          @RequestBody MemberEntity memberEntity){

        return ResponseEntity.ok(ApiResponse.success(adminService.updateAdminMember(userId,memberEntity)));
    }
}
