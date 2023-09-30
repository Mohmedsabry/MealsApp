package com.example.mealsapp.presenation.mealinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.mealsapp.R
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalGlideComposeApi::class)
@Destination
@Composable
fun MealInfoScreen(
    idMeal: String,
    navigator: DestinationsNavigator,
    mealInfoViewModel: MealInfoViewModel = hiltViewModel()
) {
    val state = mealInfoViewModel.state
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)
    SwipeRefresh(state = swipeRefreshState, onRefresh = {
        mealInfoViewModel.onEvent(MealInfoEvent.IsRefresh)
    }) {
        Column(Modifier.fillMaxSize()) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.3f)
            ) {
                GlideImage(
                    model = state.meal?.strMealThumb,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxHeight(0.9f)
                ) {
                    it.load(state.meal?.strMealThumb)
                }
                Text(
                    text = state.meal?.strMeal ?: "",
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 22.sp,
                    modifier = Modifier
                        .align(BottomCenter)
                        .padding(bottom = 30.dp),
                    color = Color.White,
                    maxLines = 1,
                )
                Row(
                    modifier = Modifier
                        .align(BottomEnd)
                        .padding(end = 30.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFDF336E))
                            .clickable {
                                mealInfoViewModel.onEvent(MealInfoEvent.OnClickedFavourite)
                            }
                    ) {
                        if (state.meal?.fav == true) {
                            Icon(
                                imageVector = Icons.Rounded.Favorite,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .align(Center)
                                    .size(35.dp)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Rounded.FavoriteBorder,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .align(Center)
                                    .size(35.dp)
                            )
                        }
                    }
                }
            }
            if (state.isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(7.dp),
                    color = Color(0xFFBD2B5C),
                )
            } else {
                Spacer(modifier = Modifier.height(20.dp))
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_category_24),
                        contentDescription = null,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                    Text(
                        text = "${stringResource(id = R.string.category)}: ${state.meal?.strCategory}",
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(horizontal = 5.dp),
                        fontSize = 18.sp
                    )
                }
                Spacer(modifier = Modifier.width(15.dp))
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_location_on_24),
                        contentDescription = null,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                    Text(
                        text = "${stringResource(id = R.string.area)}: ${state.meal?.strArea}",
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(horizontal = 5.dp),
                        fontSize = 18.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            LazyColumn(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Text(
                        text = "- ${stringResource(id = R.string.instruction)}:",
                        Modifier.padding(10.dp),
                        fontSize = 16.sp
                    )
                    Text(
                        text = state.meal?.strInstructions ?: "don't have",
                        modifier = Modifier.padding(10.dp),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Test() {
    Column(
        Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .background(Color(0xFF2196F3))
        ) {
            Row(
                modifier = Modifier
                    .align(BottomEnd)
                    .padding(end = 30.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(75.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFDF336E))
                ) {
                    if (true) {
                        Icon(
                            imageVector = Icons.Rounded.Favorite,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .align(Center)
                                .size(35.dp)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Rounded.FavoriteBorder,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .align(Center)
                                .size(35.dp)
                        )
                    }
                }

            }
        }
    }
}