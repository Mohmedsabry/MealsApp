package com.example.mealsapp.presenation.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.mealsapp.R
import com.example.mealsapp.domain.model.Meal
import com.example.mealsapp.presenation.destinations.MealInfoScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(
    ExperimentalLayoutApi::class, ExperimentalGlideComposeApi::class,
    ExperimentalMaterial3Api::class
)
@Destination
@Composable
fun SearchScreen(
    navigator: DestinationsNavigator,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    val state = searchViewModel.state
    Column(Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = state.query,
            onValueChange = {
                println(it)
                searchViewModel.onEvent(SearchEvent.OnTyping(it))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.typeMeal),
                    color = Color.LightGray
                )
            },
        )
        Spacer(Modifier.height(15.dp))
        LazyColumn(Modifier.fillMaxSize()) {
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
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }
            }
        }
    }
}