package com.ptitsa_chebupitsa.vkpostapp.domain.entity

sealed class AuthState {
    object Authorized: AuthState()
    object NotAuthorized: AuthState()
    object Initial: AuthState()
}