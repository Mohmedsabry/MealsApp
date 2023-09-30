package com.example.mealsapp.presenation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.mealsapp.R
import com.example.mealsapp.domain.model.Category
import com.example.mealsapp.presenation.component.MainBar
import com.example.mealsapp.presenation.component.ShimmerItem
import com.example.mealsapp.presenation.destinations.CategoryInfoScreenDestination
import com.example.mealsapp.presenation.destinations.MealInfoScreenDestination
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalLayoutApi::class)
@Destination(start = true,route="home")
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val state = homeViewModel.state
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.isRefreshing)
    SwipeRefresh(state = swipeRefreshState, onRefresh = {
        homeViewModel.onEvent(HomeEvents.IsRefreshing)
    }) {
        MainBar(navigator) { paddingValues ->
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                item {
                    ShimmerItem(
                        isLoading = state.isLoading,
                        flowListSize = state.categoryList?.size ?: 14,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            horizontalAlignment = CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(.3f)
                        ) {
                            Text(
                                text = stringResource(id = R.string.whatYouLikeToEat),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier
                                    .align(Alignment.Start)
                                    .padding(horizontal = 10.dp)
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            if (state.randomMeal.strMealThumb == null) {
                                Text(
                                    text = stringResource(id = R.string.ConnectionFaild),
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.align(CenterHorizontally)
                                )
                            } else {
                                GlideImage(
                                    model = state.randomMeal.strMealThumb,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(300.dp)
                                        .align(CenterHorizontally)
                                        .clip(RoundedCornerShape(12.dp))
                                        .clickable {
                                            navigator.navigate(
                                                MealInfoScreenDestination(
                                                    state.randomMeal.idMeal ?: ""
                                                )
                                            )
                                        }
                                ) {
                                    it.load(state.randomMeal.strMealThumb)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        FlowRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp),
                            maxItemsInEachRow = 3,
                            horizontalArrangement = Arrangement.Start,
                        ) {
                            repeat(state.categoryList?.size ?: 0) { index ->
                                val category = (state.categoryList?.get(index)) ?: Category()
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = CenterHorizontally,
                                    modifier = Modifier
                                        .padding(
                                            horizontal = 15.dp
                                        )
                                        .clickable {
                                            println("nav ${category.strCategory}")
                                            navigator.navigate(
                                                CategoryInfoScreenDestination(
                                                    category.strCategory ?: ""
                                                )
                                            )
                                        }
                                ) {
                                    GlideImage(
                                        model = category.strCategoryThumb,
                                        contentDescription = null,
                                        Modifier.size(70.dp)
                                    ) {
                                        it.load(category.strCategoryThumb)
                                        it.circleCrop()
                                    }
                                    Spacer(modifier = Modifier.height(5.dp))
                                    Text(
                                        text = category.strCategory ?: "fail",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp,
                                        fontStyle = FontStyle.Italic
                                    )
                                    Spacer(modifier = Modifier.height(5.dp))
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}


@Preview
@Composable
fun HomePreview() {
    // HomeScreen()
}