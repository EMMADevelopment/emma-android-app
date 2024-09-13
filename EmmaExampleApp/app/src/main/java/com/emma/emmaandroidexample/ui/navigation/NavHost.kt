package com.emma.emmaandroidexample.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.emma.emmaandroidexample.ui.home.HomeScreen
import com.emma.emmaandroidexample.ui.nativeAd.NativeAdScreen

@Composable
fun EmmaAndroidExampleNavigation(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Routes.HomeScreen.route) {
        // HOME SCREEN
        composable(Routes.HomeScreen.route) {
            HomeScreen(navController)
        }
        // NATIVE AD SCREEN
        composable(Routes.NativeAdScreen.route) {
            NativeAdScreen()
        }
    }
}