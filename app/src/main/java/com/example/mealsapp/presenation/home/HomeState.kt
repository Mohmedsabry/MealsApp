package com.example.mealsapp.presenation.home

import com.example.mealsapp.domain.model.Category
import com.example.mealsapp.domain.model.RandomMeal

data class HomeState(
    val randomMeal: RandomMeal = RandomMeal(),
    val categoryList: List<Category>? = emptyList(),
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val errorMassage: String? = null
)
