package com.haroldcalayan.mubi.presentation.main.movie_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.coil.rememberCoilPainter
import com.haroldcalayan.mubi.BuildConfig
import com.haroldcalayan.mubi.R
import java.math.BigDecimal
import java.math.RoundingMode

@Composable
fun MovieDetailsScreen(
    navController: NavController,
    movieDetailsViewModel: MovieDetailsViewModel = hiltViewModel()
) {

    val state = movieDetailsViewModel.state.value

    state.movie?.let { movie ->
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    painter = if (movie.backdropPath == null) painterResource(
                        id = R.drawable.ic_launcher_foreground
                    ) else rememberCoilPainter(request = BuildConfig.BASE_IMAGE_URL + movie.backdropPath),
                    contentDescription = "Movie",
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black
                                ),
                                startY = 300f
                            )
                        )
                        .fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp),
                    contentAlignment = Alignment.TopStart,
                ) {
                    IconButton(
                        modifier = Modifier
                            .alpha(ContentAlpha.medium),
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        movie.title?.let {
                            Text(
                                text = it,
                                style = TextStyle(
                                    color = Color.White,
                                    fontSize = 24.sp,
                                )
                            )
                        }

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
                                    tint = Color.White
                                )
                            }

                            if (halfStar) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_star_half),
                                    contentDescription = "star",
                                    tint = Color.White
                                )
                            }

                            repeat(unfilledStars) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_star_border),
                                    contentDescription = "star",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
            }
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(id = R.string.details_summary),
                    style = MaterialTheme.typography.h6,
                    color = colorResource(R.color.primary)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = movie.overview.orEmpty(),
                    style = TextStyle(
                        color = colorResource(id = R.color.details_font_description),
                        fontSize = 14.sp
                    ),
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}