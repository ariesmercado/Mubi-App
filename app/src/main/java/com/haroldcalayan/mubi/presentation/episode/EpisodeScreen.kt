package com.haroldcalayan.mubi.presentation.episode

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.coil.rememberCoilPainter
import com.haroldcalayan.mubi.BuildConfig
import com.haroldcalayan.mubi.R
import com.haroldcalayan.mubi.R.color
import com.haroldcalayan.mubi.R.drawable

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EpisodeScreen(
    navController: NavController,
    episodeViewModel: EpisodeViewModel = hiltViewModel()
) {

    val state = episodeViewModel.state.value

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (state.error.isNotBlank()) {
                TopAppBar(
                    title = {
                        state.season?.name?.let { Text(text = it) }
                    },
                    backgroundColor = colorResource(id = color.primaryDark),
                    contentColor = colorResource(id = color.white),
                    elevation = 12.dp,
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, "Back")
                        }
                    }
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = drawable.baseline_error_24),
                        contentDescription = "Error",
                        modifier = Modifier
                            .width(100.dp)
                            .height(100.dp),
                        contentScale = ContentScale.Crop,
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "No data found",
                        color = Color.Red,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    )
                }
            } else {
                state.season?.episodes.let { episode ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TopAppBar(
                            title = {
                                state.season?.name?.let { Text(text = it) }
                            },
                            backgroundColor = colorResource(id = color.primaryDark),
                            contentColor = colorResource(id = color.white),
                            elevation = 12.dp,
                            navigationIcon = {
                                IconButton(onClick = { navController.popBackStack() }) {
                                    Icon(Icons.Default.ArrowBack, "Back")
                                }
                            }
                        )
                        LazyVerticalGrid(
                            state = rememberLazyListState(),
                            cells = GridCells.Fixed(1),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(episode ?: emptyList()) { season ->
                                Box(
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .padding(16.dp)
                                ) {
                                    Card(
                                        modifier = Modifier.wrapContentSize(),
                                        shape = RoundedCornerShape(15.dp),
                                        elevation = 5.dp,
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(Color.White),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Image(
                                                painter = if (season.stillPath == null) painterResource(
                                                    id = drawable.ic_launcher_foreground
                                                ) else rememberCoilPainter(request = BuildConfig.BASE_IMAGE_URL + season.stillPath),
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
                                                        fontWeight = FontWeight.SemiBold,
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
                                                season.runtime?.let {
                                                    Text(
                                                        text = "$it",
                                                        style = TextStyle(
                                                            color = colorResource(id = R.color.primary),
                                                            fontSize = 16.sp
                                                        ),
                                                        color = Color.Blue
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
        }
    }
}