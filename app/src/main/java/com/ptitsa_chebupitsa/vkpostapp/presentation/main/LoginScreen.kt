package com.ptitsa_chebupitsa.vkpostapp.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ptitsa_chebupitsa.vkpostapp.R
import com.ptitsa_chebupitsa.vkpostapp.ui.theme.DarkBlue


@Composable
fun LoginScreen(
    onLoginClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier.wrapContentHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(100.dp),
                painter = painterResource(id = R.drawable.vk_1_logo_svgrepo_com),
                contentDescription = null
            )
            Spacer(modifier = Modifier.size(100.dp))
            Button(   colors = ButtonDefaults.buttonColors(
                containerColor = DarkBlue,
                contentColor = Color.White
            ), onClick = { onLoginClick()
            }) {
                Text(text = "Войти с помощью VK")
            }
        }
    }
}