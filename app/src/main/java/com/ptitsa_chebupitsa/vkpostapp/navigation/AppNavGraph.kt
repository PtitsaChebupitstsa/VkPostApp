package com.ptitsa_chebupitsa.vkpostapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ptitsa_chebupitsa.vkpostapp.domain.entity.FeedPost

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    newsFeedScreenContent:@Composable () -> Unit,
    favoriteScreenContent: @Composable () -> Unit,
    profileScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (feedPost: FeedPost) -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route
    ) {

        homeScreenNavGraph(commentsScreenContent =commentsScreenContent
            ,newsFeedScreenContent = newsFeedScreenContent)
        
        composable(route = Screen.Favorite.route){
            favoriteScreenContent ()
        }
        composable(route = Screen.Profile.route){
            profileScreenContent()
        }
    }
}