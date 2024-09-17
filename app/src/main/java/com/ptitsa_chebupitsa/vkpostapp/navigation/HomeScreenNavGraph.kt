package com.ptitsa_chebupitsa.vkpostapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.ptitsa_chebupitsa.vkpostapp.domain.entity.FeedPost

fun NavGraphBuilder.homeScreenNavGraph(
    commentsScreenContent: @Composable (feedPost: FeedPost) -> Unit,
    newsFeedScreenContent: @Composable () -> Unit
) {
    navigation(
        startDestination = Screen.NewsFeed.route,
        route = Screen.Home.route,
        builder = {
            composable(route = Screen.NewsFeed.route) {
                newsFeedScreenContent()
            }
            composable(
                route = Screen.Comments.route,
                arguments = listOf(
                    navArgument(Screen.KEY_FEED_POST){
                        type = FeedPost.NavigationType
                    },
                )
            ) {
                val feedPost = it.arguments?.getParcelable<FeedPost>(Screen.KEY_FEED_POST) ?: throw RuntimeException ("Args is null")
                commentsScreenContent(feedPost)
            }
        }
    )
}