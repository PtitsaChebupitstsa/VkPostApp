package com.ptitsa_chebupitsa.vkpostapp.presentation.main

sealed class AuthState {
    object Authorized: AuthState()
    object NotAuthorized: AuthState()
    object Initial: AuthState()
}