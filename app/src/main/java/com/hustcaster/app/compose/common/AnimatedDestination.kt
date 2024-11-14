package com.hustcaster.app.compose.common

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder

//fun NavGraphBuilder.animatedComposable(
//    route: String,
//    arguments: List<NamedNavArgument> = emptyList(),
//    deepLinks: List<NavDeepLink> = emptyList(),
//    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
//) = composable(

//)
//    composable(
//    route=route,
//    arguments=arguments,
//    deepLinks=deepLinks,
//    enterTransition={
//        fadeIn(animationSpec = tween(220, delayMillis = 90))+
//                scaleIn(
//                    initialScale = 0.92f,
//                    animationSpec = tween(220, delayMillis = 90)
//                )
//    },
//
//)