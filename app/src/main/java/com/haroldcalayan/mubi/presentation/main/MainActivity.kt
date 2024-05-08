package com.haroldcalayan.mubi.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.haroldcalayan.mubi.R
import com.haroldcalayan.mubi.common.ui.theme.MubiTheme
import com.haroldcalayan.mubi.presentation.episode.EpisodeScreen
import com.haroldcalayan.mubi.presentation.main.movie_details.MovieDetailsScreen
import com.haroldcalayan.mubi.presentation.main.movie_list.MovieListScreen
import com.haroldcalayan.mubi.presentation.main.profile.ProfileScreen
import com.haroldcalayan.mubi.presentation.main.tv_details.TVDetailsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MubiTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination?.route
                Scaffold(
                    topBar = {
                        if (currentDestination == "movie_list_screen") {
                            TopAppBar(
                                title = {
                                    Text(text = "TV Shows")
                                },
                                backgroundColor = colorResource(id = R.color.primaryDark),
                                contentColor = colorResource(id = R.color.white),
                                elevation = 12.dp,
                                actions = {
                                    var expandedSearchBar by remember {
                                        mutableStateOf(false)
                                    }
                                    if (expandedSearchBar) {
                                        SearchBar(
                                            text = "",
                                            onTextChanged = { /*TODO*/ },
                                            onCloseClick = { expandedSearchBar = false },
                                            onSearchClick = { /*TODO*/ }
                                        )
                                    }
                                    IconButton(onClick = { expandedSearchBar = true }) {
                                        Icon(Icons.Rounded.Search, "Search")
                                    }
                                    IconButton(onClick = {
                                        navController.navigate(Screen.ProfileScreen.route)
                                    }) {
                                        Icon(Icons.Rounded.Person, "Profile")
                                    }
                                }
                            )
                        }
                    }, content = {
                        Surface(color = MaterialTheme.colors.background) {
                            NavHost(
                                navController = navController,
                                startDestination = Screen.MovieListScreen.route
                            ) {
                                composable(
                                    route = Screen.MovieListScreen.route
                                ) {
                                    MovieListScreen(navController)
                                }
                                composable(
                                    route = Screen.MovieDetailScreen.route + "/{movieId}"
                                ) {
                                    MovieDetailsScreen(navController)
                                }
                                composable(
                                    route = Screen.TVDetailScreen.route + "/{tvId}"
                                ) {
                                    TVDetailsScreen(navController)
                                }
                                composable(
                                    route = Screen.EpisodeScreen.route + "/{tvId}/{seasonNumber}"
                                ) {
                                    EpisodeScreen(navController)
                                }
                                composable(
                                    route = Screen.ProfileScreen.route
                                ) {
                                    ProfileScreen(this@MainActivity, navController)
                                }
                            }
                        }
                    })

            }
        }
    }

    @Composable
    fun SearchBar(
        text: String,
        onTextChanged: (String) -> Unit,
        onCloseClick: () -> Unit,
        onSearchClick: (String) -> Unit
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            elevation = AppBarDefaults.TopAppBarElevation,
            color = colorResource(id = R.color.primaryDark)
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = text,
                onValueChange = {
                    onTextChanged(it)
                },
                placeholder = {
                    Text(
                        modifier = Modifier
                            .alpha(ContentAlpha.medium),
                        text = "Search...",
                        color = Color.White
                    )
                },
                textStyle = TextStyle(
                    fontSize = MaterialTheme.typography.subtitle1.fontSize
                ),
                singleLine = true,
                leadingIcon = {
                    IconButton(
                        modifier = Modifier
                            .alpha(ContentAlpha.medium),
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Bar",
                            tint = Color.White
                        )
                    }
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            if (text.isNotEmpty()) {
                                onTextChanged("")
                            } else {
                                onCloseClick()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Bar",
                            tint = Color.White
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearchClick(text)
                    }
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    cursorColor = Color.White.copy(alpha = ContentAlpha.medium)
                )
            )
        }
    }
}






