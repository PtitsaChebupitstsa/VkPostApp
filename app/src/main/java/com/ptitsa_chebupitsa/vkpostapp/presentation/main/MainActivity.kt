package com.ptitsa_chebupitsa.vkpostapp.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel

import com.ptitsa_chebupitsa.vkpostapp.ui.theme.VkPostAppTheme
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope

class MainActivity : ComponentActivity() {


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContent {
                VkPostAppTheme {
                    val viewModel: MainViewModel = viewModel()
                    val authState = viewModel.authState.observeAsState(AuthState.Initial)
                    val launcher = rememberLauncherForActivityResult(
                        contract = VK.getVKAuthActivityResultContract()
                    ) { result ->
                        viewModel.performAuthResult(result)
                    }
                    when (authState.value) {
                        is AuthState.Authorized -> VkNewsMainScreen()
                        is AuthState.NotAuthorized -> LoginScreen {
                            launcher.launch(listOf(VKScope.WALL, VKScope.FRIENDS))
                        }
                        else -> {}
                    }
                }
            }
        }
    }


