package com.ptitsa_chebupitsa.vkpostapp.presentation.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold

import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ptitsa_chebupitsa.vkpostapp.navigation.AppNavGraph
import com.ptitsa_chebupitsa.vkpostapp.navigation.rememberNavigationState
import com.ptitsa_chebupitsa.vkpostapp.presentation.comments.CommentsScreen
import com.ptitsa_chebupitsa.vkpostapp.presentation.news.NewsFeedScreen

@Composable
fun VkNewsMainScreen() {

    val navigationState = rememberNavigationState()




    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
                val items =
                    listOf(NavigationItem.Home, NavigationItem.Favorite, NavigationItem.Profile)

                items.forEach { item ->

                    val selected = navBackStackEntry?.destination?.hierarchy?.any {
                        it.route == item.screen.route
                    } ?: false

                    NavigationBarItem(

                        selected = selected,
                        label = { Text(stringResource(id = item.titleResId)) },
                        onClick = {
                            if(!selected) {
                                navigationState.navigateTo(item.screen.route)
                            }
                        },
                        icon = {
                            Icon(imageVector = item.icon, contentDescription = null)
                        }, colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSecondary,
                            unselectedTextColor = MaterialTheme.colorScheme.onSecondary,
                            indicatorColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
        },
    ) { paddingValues ->
        AppNavGraph(
            navHostController = navigationState.navHostController,
            newsFeedScreenContent = {
                NewsFeedScreen(
                    paddingValues = paddingValues,
                    onCommentsClickListener = {

                        navigationState.navigateToComments(it)
                    }
                )
            },
            favoriteScreenContent = { TextCounter("Favorite") },
            profileScreenContent = { TextCounter("Profile") },
            commentsScreenContent = {feedPost->
                CommentsScreen(
                    onBackPressed = { navigationState.navHostController.popBackStack() },
                    feedPost = feedPost
                )
            }
        )
    }
}

@Composable
private fun TextCounter(name: String) {
    var count by rememberSaveable {
        mutableIntStateOf(0)
    }
    Text(text = "$name нажато $count", color = Color.Red, modifier = Modifier
        .clickable { count++ }
        .padding(20.dp))

}







