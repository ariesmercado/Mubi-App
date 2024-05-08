@file:OptIn(ExperimentalFoundationApi::class)

package com.haroldcalayan.mubi.presentation.main.tv_details

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.coil.rememberCoilPainter
import com.haroldcalayan.mubi.BuildConfig
import com.haroldcalayan.mubi.R
import com.haroldcalayan.mubi.presentation.main.Screen
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.ceil

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TVDetailsScreen(
    navController: NavController,
    tvDetailsViewModel: TVDetailsViewModel = hiltViewModel()
) {
    val state = tvDetailsViewModel.state

    state.value.tv?.let { tv ->
        Column(
            modifier = Modifier.fillMaxSize()
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
                    painter = if (tv.backdropPath == null) painterResource(
                        id = R.drawable.ic_launcher_foreground
                    ) else rememberCoilPainter(request = BuildConfig.BASE_IMAGE_URL + tv.backdropPath),
                    contentDescription = "Movie",
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent, Color.Black
                                ), startY = 300f
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
                        tv.originalName?.let {
                            Text(
                                text = it, style = TextStyle(
                                    color = Color.White,
                                    fontSize = 24.sp,
                                )
                            )
                        }

                        val rate by remember {
                            mutableStateOf(
                                BigDecimal(5 * ((tv.voteAverage ?: 0.0) / 10.0))
                                    .setScale(1, RoundingMode.HALF_EVEN).toDouble()
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            val maxRate = rate.toInt()
                            val unfilledStars = (5 - ceil(rate)).toInt()
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
                    text = tv.overview.orEmpty(),
                    style = TextStyle(color = colorResource(id = R.color.details_font_description), fontSize = 14.sp),
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            LazyVerticalGrid(
                state = rememberLazyListState(),
                cells = GridCells.Fixed(1),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(state.value.tv?.seasons ?: emptyList()) { season ->
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(16.dp)
                    ) {
                        Card(
                            modifier = Modifier.wrapContentSize(),
                            shape = RoundedCornerShape(15.dp),
                            elevation = 5.dp,
                            onClick = {
                                navController.navigate(Screen.EpisodeScreen.route + "/${season.id}/${season.seasonNumber}")
                            }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.White),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = if(season.posterPath == null) painterResource(id = R.drawable.ic_launcher_foreground) else rememberCoilPainter(request = BuildConfig.BASE_IMAGE_URL + season.posterPath),
                                    contentDescription = "Movie",
                                    modifier = Modifier
                                        .width(150.dp)
                                        .height(125.dp),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(
                                    modifier = Modifier.padding(12.dp)
                                ) {
                                    season.name?.let {
                                        Text(
                                            text = it,
                                            style = MaterialTheme.typography.h6,
                                            color = colorResource(id = R.color.details_font_title),
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                    season.episodeCount?.let {
                                        Text(
                                            text = "$it episodes",
                                            style = TextStyle(color = Color.Blue, fontSize = 12.sp),
                                            color = colorResource(id = R.color.primary)
                                        )
                                    }
                                    season.overview?.let {
                                        Text(
                                            text = it,
                                            style = TextStyle(color = colorResource(id = R.color.details_font_description), fontSize = 14.sp),
                                            maxLines = 3,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


