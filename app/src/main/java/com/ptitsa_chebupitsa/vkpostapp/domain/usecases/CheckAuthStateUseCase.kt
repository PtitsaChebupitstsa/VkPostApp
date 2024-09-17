package com.ptitsa_chebupitsa.vkpostapp.domain.usecases

import com.ptitsa_chebupitsa.vkpostapp.domain.repository.NewsFeedRepository

class CheckAuthStateUseCase (
    private val repository: NewsFeedRepository
){
    suspend operator fun invoke() {
       return repository.checkAuthState()
    }
}