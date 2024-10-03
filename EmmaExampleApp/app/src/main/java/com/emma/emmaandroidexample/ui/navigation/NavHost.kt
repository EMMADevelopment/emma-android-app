package com.emma.emmaandroidexample.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.emma.emmaandroidexample.MainViewModel
import com.emma.emmaandroidexample.ui.home.HomeScreen
import com.emma.emmaandroidexample.ui.home.HomeViewModel
import com.emma.emmaandroidexample.ui.nativeAd.NativeAdScreen
import com.emma.emmaandroidexample.ui.nativeAd.NativeAdViewModel

@Composable
fun EmmaAndroidExampleNavigation(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    sessionStarted: Boolean,
) {
    NavHost(navController = navController, startDestination = Routes.HomeScreen.route) {
        // HOME SCREEN
        composable(Routes.HomeScreen.route) {
            HomeScreen(navController, homeViewModel, sessionStarted)
        }
        // NATIVE AD SCREEN
        composable(Routes.NativeAdScreen.route) {
            NativeAdScreen()
        }
    }
}