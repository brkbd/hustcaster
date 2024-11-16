package com.hustcaster.app.compose.common

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hustcaster.app.App.Companion.context
import com.hustcaster.app.R
import com.hustcaster.app.compose.NavControllerSingleton
import com.hustcaster.app.compose.getTopDestinationRoute

@Preview
@Composable
fun NavigationBarImpl() {
    val navController:NavHostController=NavControllerSingleton.getInstance(rememberNavController())
    var selectedTab by remember {
        mutableIntStateOf(0)
    }
    navController.addOnDestinationChangedListener { _, _, _ ->
        selectedTab = navController.getTopDestinationRoute()
    }
    NavigationBar {
        NavigationBarItem(
            selected = selectedTab == 0,
            onClick = {
                navController.navigate(NavigationGraph.HOME) {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(NavigationGraph.HOME) {
                        saveState = true
                    }
                }
            },
            icon = {
                Icon(
                    imageVector = if (selectedTab != 0) Icons.Outlined.Home else Icons.Filled.Home,
                    contentDescription = null
                )
            },
            label = { Text(text = stringResource(id = R.string.home)) }
        )
        NavigationBarItem(
            selected = selectedTab == 1,
            onClick = {
                navController.navigate(NavigationGraph.SUBSCRIPTION) {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(NavigationGraph.HOME) {
                        saveState = true
                    }
                }
            },
            icon = {
                Icon(
                    imageVector = if (selectedTab != 1) Icons.Outlined.MailOutline else Icons.Filled.MailOutline,
                    contentDescription = null
                )
            },
            label = { Text(text = stringResource(id = R.string.subscription)) }
        )
        NavigationBarItem(
            selected = selectedTab == 2,
            onClick = {
                navController.navigate(NavigationGraph.RSS) {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(NavigationGraph.HOME) {
                        saveState = true
                    }
                }
            },
            icon = {
                Icon(
                    imageVector = if (selectedTab != 2) Icons.Outlined.Star else Icons.Filled.Star,
                    contentDescription = null
                )
            },
            label = { Text(text = stringResource(id = R.string.rss)) }
        )
    }
}