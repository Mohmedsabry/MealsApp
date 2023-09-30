package com.example.mealsapp.presenation.categoryInfo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.mealsapp.domain.model.Meal
import com.example.mealsapp.presenation.destinations.MealInfoScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalLayoutApi::class, ExperimentalGlideComposeApi::class)
@Destination
@Composable
fun CategoryInfoScreen(
    strCategory: String,
    navigator: DestinationsNavigator,
    viewModel: CategoryInfoViewModel = hiltViewModel()
) {
    val state = viewModel.state
    LazyColumn(Modifier.fillMaxSize()) {
        item {
            Column(Modifier.fillMaxWidth()) {
                Text(
                    text = "$strCategory : ${state.mealList?.size}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(vertical = 12.dp),
                    color = Color(0xFFEC3D78)
                )
                Spacer(modifier = Modifier.height(15.dp))
            }
        }
        item {
            FlowRow(
                modifier = Modifier.fillMaxSize(),
                maxItemsInEachRow = 2,
                horizontalArrangement = Arrangement.Start,
            ) {
                repeat(state.mealList?.size ?: 0) { index ->
                    val mealItem = state.mealList?.get(index) ?: Meal()
                    OutlinedCard(
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth(.5f)
                            .padding(horizontal = 10.dp, vertical = 10.dp)
                            .clickable {
                                navigator.navigate(
                                    MealInfoScreenDestination(
                                        mealItem.idMeal ?: ""
                                    )
                                )
                            },
                        elevation = CardDefaults.cardElevation(10.dp)
                    ) {
                        GlideImage(
                            model = mealItem.strMealThumb,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp),
                            contentScale = ContentScale.FillBounds
                        ) {
                            it.load(mealItem.strMealThumb ?: "")
                        }
                        Text(
                            text = mealItem.strMeal ?: "",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(CenterHorizontally)
                        )
                    }
                }
            }
        }
    }
    if (state.isLoading) {
        Box(
            Modifier
                .fillMaxSize()
                .background(
                    color = Color.White.copy(0.7f)
                )
        ) {
            CircularProgressIndicator(
                color = Color(0xFFE93F79),
                modifier = Modifier.align(Center)
            )
        }
    }
}