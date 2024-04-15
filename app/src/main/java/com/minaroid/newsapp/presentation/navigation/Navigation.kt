package com.minaroid.newsapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.minaroid.newsapp.presentation.screens.DetailsScreen
import com.minaroid.newsapp.presentation.screens.HomeScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavigationItem.HomeScreenNavigationItem.route
    ) {
        composable(NavigationItem.HomeScreenNavigationItem.route,) {
            HomeScreen(navController)
        }

        composable(NavigationItem.DetailsScreenNavigationItem.route) {
            val title = it.arguments?.getString("title")
            DetailsScreen(title.toString())
        }
    }
}