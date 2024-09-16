package com.emma.emmaandroidexample.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.emma.emmaandroidexample.ui.home.HomeScreen
import com.emma.emmaandroidexample.ui.nativeAd.NativeAdScreen

@Composable
fun EmmaAndroidExampleNavigation(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Routes.HomeScreen.createRoute()) {
        // HOME SCREEN
        composable(Routes.HomeScreen.createRoute()) {
            HomeScreen(navController, null)
        }
        // HOME SCREEN
        composable(
            Routes.HomeScreen.route,
            arguments = listOf(navArgument("deeplink") { defaultValue = "" })
        ) {
            val deeplink = it.arguments?.getString("deeplinkUrl")
            HomeScreen(navController = navController, deeplink)
        }
        // NATIVE AD SCREEN
        composable(Routes.NativeAdScreen.route) {
            NativeAdScreen()
        }
    }
}