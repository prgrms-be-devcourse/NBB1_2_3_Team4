package com.example.Nadeuri.member.admin

import com.example.Nadeuri.common.response.ApiResponse
import com.example.Nadeuri.member.MemberEntity
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/v1/admin")
class AdminController(private val adminService: AdminService) {

    // 멤버 전체 조회
    @GetMapping
    fun selectAllMembers(): ResponseEntity<ApiResponse<*>> {
        return ResponseEntity.ok(ApiResponse.success(adminService.adminMemberAll))
    }

    // 멤버 조회
    @GetMapping("/{userId}")
    fun selectMember(@PathVariable userId: String): ResponseEntity<ApiResponse<*>> {
        return ResponseEntity.ok(ApiResponse.success(adminService.getAdminMember(userId)))
    }

    // 멤버 삭제
    @DeleteMapping("/{userId}")
    fun deleteMember(@PathVariable userId: String): ResponseEntity<ApiResponse<*>> {
        adminService.deleteAdminMember(userId)
        return ResponseEntity.ok(ApiResponse.success("계정 삭제 완료"))
    }

    // 멤버 수정
    @PutMapping("/{userId}")
    fun updateMember(
        @PathVariable userId: String,
        @RequestBody memberEntity: MemberEntity
    ): ResponseEntity<ApiResponse<*>> {
        return ResponseEntity.ok(ApiResponse.success(adminService.updateAdminMember(userId, memberEntity)))
    }
}