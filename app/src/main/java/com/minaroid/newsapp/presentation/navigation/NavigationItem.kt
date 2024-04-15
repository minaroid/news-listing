package com.minaroid.newsapp.presentation.navigation

sealed class NavigationItem(val route: String, val label: String) {
    data object HomeScreenNavigationItem : NavigationItem("home_screen", "Home")
    data object DetailsScreenNavigationItem : NavigationItem("details_screen/{title}",  "Details")
}
