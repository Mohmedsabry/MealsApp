package com.example.mealsapp.presenation.fav

import com.example.mealsapp.domain.model.Meal

data class FavState(
    val mealList: List<Meal>? = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
