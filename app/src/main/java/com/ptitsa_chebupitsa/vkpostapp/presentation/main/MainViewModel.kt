package com.ptitsa_chebupitsa.vkpostapp.presentation.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ptitsa_chebupitsa.vkpostapp.data.repository.NewsFeedRepositoryImpl
import com.ptitsa_chebupitsa.vkpostapp.domain.usecases.CheckAuthStateUseCase
import com.ptitsa_chebupitsa.vkpostapp.domain.usecases.GetAuthStateFlowUseCase
import kotlinx.coroutines.launch

class MainViewModel(application: Application):AndroidViewModel(application) {

    private val repository = NewsFeedRepositoryImpl(application)
    private val getAuthStateFlowUseCase = GetAuthStateFlowUseCase(repository)
    private val checkAuthStateUseCase = CheckAuthStateUseCase(repository)

    val authState=  getAuthStateFlowUseCase()

    fun performAuthResult (){
       viewModelScope.launch {
         checkAuthStateUseCase()
       }
    }

}