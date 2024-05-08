package com.haroldcalayan.mubi.presentation.main.movie_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.coil.rememberCoilPainter
import com.haroldcalayan.mubi.BuildConfig
import com.haroldcalayan.mubi.R
import com.haroldcalayan.mubi.common.Constants.CATEGORY_AIRING_TODAY
import com.haroldcalayan.mubi.common.Constants.CATEGORY_ON_TV
import com.haroldcalayan.mubi.common.Constants.CATEGORY_POPULAR
import com.haroldcalayan.mubi.common.Constants.CATEGORY_TOP_RATED
import com.haroldcalayan.mubi.presentation.main.Screen
import java.math.BigDecimal
import java.math.RoundingMode

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun MovieListScreen(
    navController: NavController,
    movieListViewModel: MovieListViewModel = hiltViewModel()
) {
    val state = movieListViewModel.state
    val categories = listOf(
        CATEGORY_TOP_RATED,
        CATEGORY_POPULAR,
        CATEGORY_ON_TV,
        CATEGORY_AIRING_TODAY
    )

    var selectedOption by remember { mutableStateOf(CATEGORY_TOP_RATED) }
    val onSelectionChange = { text: String -> selectedOption = text }

    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items(categories) { categoryName ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = categoryName,
                        style = TextStyle(
                            color = if (selectedOption == categoryName) colorResource(R.color.tabs_font_selected) else colorResource(
                                R.color.tabs_font_default
                            ),
                            fontSize = 12.sp
                        ),
                        modifier = Modifier
                            .clip(RoundedCornerShape(100.dp))
                            .clickable {
                                onSelectionChange(categoryName)
                                when (categoryName) {
                                    CATEGORY_TOP_RATED -> movieListViewModel.getTopRatedMovies()
                                    CATEGORY_POPULAR -> movieListViewModel.getPopularMovies()
                                    CATEGORY_ON_TV -> movieListViewModel.getOnTvMovies()
                                    CATEGORY_AIRING_TODAY -> movieListViewModel.getAiringToday()
                                }
                            }
                            .background(
                                if (categoryName == selectedOption) {
                                    colorResource(R.color.tabs_background_selected)
                                } else {
                                    colorResource(R.color.tabs_background_default)
                                }
                            )
                            .padding(
                                vertical = 12.dp,
                                horizontal = 12.dp,
                            )
                    )
                }
            }
        }

        LazyVerticalGrid(
            modifier = Modifier.weight(1f, true),
            state = rememberLazyListState(),
            cells = GridCells.Fixed(2)
        ) {
            items(state.value.popularMovies?.results ?: emptyList()) { movie ->
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(8.dp)
                ) {
                    Card(
                        modifier = Modifier.wrapContentSize(),
                        shape = RoundedCornerShape(15.dp),
                        elevation = 5.dp,
                        onClick = {
                            if (movie.releaseDate != null) {
                                navController.navigate(Screen.MovieDetailScreen.route + "/${movie.id}")
                            } else {
                                navController.navigate(Screen.TVDetailScreen.route + "/${movie.id}")
                            }
                        }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = if (movie.backdropPath == null) painterResource(
                                    id = R.drawable.ic_launcher_foreground
                                ) else rememberCoilPainter(request = BuildConfig.BASE_IMAGE_URL + movie.backdropPath),
                                contentDescription = "Movie",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.White)
                                    .padding(8.dp)
                            ) {
                                val title =
                                    if (movie.title?.isNotEmpty() == true) movie.title else movie.originalName
                                Text(
                                    text = title.orEmpty(),
                                    style = TextStyle(
                                        color = colorResource(id = R.color.list_font_title),
                                        fontSize = 15.sp
                                    ),
                                    fontWeight = FontWeight.SemiBold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                val rate by remember {
                                    mutableStateOf(
                                        BigDecimal(5 * ((movie.voteAverage ?: 0.0) / 10.0))
                                            .setScale(1, RoundingMode.HALF_EVEN).toDouble()
                                    )
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    val maxRate = rate.toInt()
                                    val unfilledStars = (5 - kotlin.math.ceil(rate)).toInt()
                                    val halfStar = !(rate.rem(1).equals(0.0))

                                    repeat(maxRate) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.baseline_star_rate),
                                            contentDescription = "star",
                                            Modifier.height(20.dp),
                                            tint = colorResource(R.color.list_background_star_rating)
                                        )
                                    }

                                    if (halfStar) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.baseline_star_half),
                                            contentDescription = "star",
                                            Modifier.height(20.dp),
                                            tint = colorResource(R.color.list_background_star_rating)
                                        )
                                    }

                                    repeat(unfilledStars) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.baseline_star_border),
                                            contentDescription = "star",
                                            Modifier.height(20.dp),
                                            tint = colorResource(R.color.list_background_star_rating)
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(2.dp))

                                    Text(
                                        text = rate.toString(),
                                        style = TextStyle(
                                            color = colorResource(R.color.list_font_star_rating),
                                            fontSize = 15.sp
                                        ),
                                        maxLines = 1
                                    )
                                }
                                Spacer(modifier = Modifier.height(12.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

