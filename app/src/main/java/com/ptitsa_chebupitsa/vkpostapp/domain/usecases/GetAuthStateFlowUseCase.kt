package com.ptitsa_chebupitsa.vkpostapp.domain.usecases

import com.ptitsa_chebupitsa.vkpostapp.domain.entity.AuthState
import com.ptitsa_chebupitsa.vkpostapp.domain.repository.NewsFeedRepository
import kotlinx.coroutines.flow.StateFlow

class GetAuthStateFlowUseCase (
    private val repository: NewsFeedRepository
){
    operator fun invoke(): StateFlow<AuthState> {
       return repository.getAuthStateFlow()
    }
}