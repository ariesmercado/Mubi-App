package com.haroldcalayan.mubi.presentation.main

sealed class Screen(val route: String) {
    object MovieListScreen : Screen("movie_list_screen")
    object MovieDetailScreen : Screen("movie_detail_screen")
    object TVDetailScreen : Screen("tv_detail_screen")
    object EpisodeScreen : Screen("season_detail_screen")
    object ProfileScreen : Screen("profile_screen")
}