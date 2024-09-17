package com.ptitsa_chebupitsa.vkpostapp.domain

sealed class AuthState {
    object Authorized: AuthState()
    object NotAuthorized: AuthState()
    object Initial: AuthState()
}