package com.example.Nadeuri.member.security.auth

import java.security.Principal

class CustomUserPrincipal(
    private val mid: String
) : Principal {
    override fun getName(): String {
        return mid
    }
}
