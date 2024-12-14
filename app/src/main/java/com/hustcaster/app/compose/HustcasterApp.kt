package com.hustcaster.app.compose

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hustcaster.app.compose.common.NavigationGraph
import com.hustcaster.app.compose.common.NavigationGraph.PODCAST_ID
import com.hustcaster.app.compose.common.NavigationGraph.toId
import com.hustcaster.app.compose.common.NavigationGraph.withArgument
import com.hustcaster.app.compose.home.HomeScreen
import com.hustcaster.app.compose.listen.ListenScreen
import com.hustcaster.app.compose.podcast.PodcastInfoScreen
import com.hustcaster.app.compose.podcast.PodcastListScreen
import com.hustcaster.app.compose.podcast.PodcastScreen
import com.hustcaster.app.compose.record.RecordListScreen
import com.hustcaster.app.compose.rss.RssScreen
import com.hustcaster.app.compose.subscription.UpdateListScreen
import com.hustcaster.app.data.AppDatabase
import com.hustcaster.app.viewmodels.PodcastViewModel

@Composable
fun HustcasterApp() {
    val navController = NavControllerSingleton.getInstance(rememberNavController())
    HustcasterNavHost(navController = navController)
}

@Composable
fun HustcasterNavHost(
    navController: NavHostController
) {
    val activity = (LocalContext.current as Activity)
    val appDatabase = AppDatabase.getDatabase(LocalContext.current)
    NavHost(navController = navController, startDestination = NavigationGraph.HOME) {
        composable(route = NavigationGraph.HOME) {
            HomeScreen(
                onMoreRecordClick = { navController.navigate(NavigationGraph.RECORD) },
                onMorePodcastClick = { navController.navigate(NavigationGraph.PODCAST_LIST) },
                onPodcastClick = {
                    navController.navigate(
                        NavigationGraph.PODCAST.toId(it.podcast.id)
                    )
                },
                navigateToListenPage = {
                    navController.navigate(NavigationGraph.LISTEN)
                }
            )
        }
        composable(route = NavigationGraph.SUBSCRIPTION) {
            UpdateListScreen {
                navController.navigate(NavigationGraph.LISTEN)
            }
        }
        composable(route = NavigationGraph.RSS) {
            RssScreen()
        }
        composable(route = NavigationGraph.RECORD) {
            RecordListScreen(
                onPlayClick = {
                    navController.navigate(NavigationGraph.LISTEN)
                },
                onNavigationIconClick = { navController.navigateUp() }
            )
        }
        composable(route = NavigationGraph.PODCAST_LIST) {
            PodcastListScreen(
                onBackClick = { navController.navigateUp() },
                onPodcastClick = {
                    navController.navigate(NavigationGraph.PODCAST.toId(it.podcast.id))
                }
            )
        }
        composable(
            route = NavigationGraph.PODCAST.withArgument(PODCAST_ID),
            arguments = listOf(navArgument(PODCAST_ID) {
                type = NavType.LongType
            })
        ) {
            PodcastScreen(
                onInfoClick = { navController.navigate(NavigationGraph.PODCAST_INFO) },
                onBackClick = { navController.navigateUp() },
                onPlayAllClick = {},
                navigateToListenPage = {
                    navController.navigate(NavigationGraph.LISTEN)
                })
        }

        composable(
            route = NavigationGraph.PODCAST_INFO
        ) {
            val parentEntry = remember {
                navController.getBackStackEntry(NavigationGraph.PODCAST.withArgument(PODCAST_ID))
            }
            val viewModel = hiltViewModel<PodcastViewModel>(parentEntry)
            PodcastInfoScreen(viewModel) {
                navController.navigateUp()
            }
        }

        composable(
            route = NavigationGraph.LISTEN
        ) {
            ListenScreen(
                onDismiss = {
                    navController.navigateUp()
                }
            )
        }
    }
}

fun NavHostController.getTopDestinationRoute(): Int {
    this.backQueue.reversed().forEach {
        with(it.destination.route) {
            when (this) {
                NavigationGraph.HOME -> return 0
                NavigationGraph.SUBSCRIPTION -> return 1
                NavigationGraph.RSS -> return 2
            }
        }
    }
    return 3
}
