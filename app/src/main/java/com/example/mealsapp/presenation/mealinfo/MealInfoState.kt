package com.example.mealsapp.presenation.mealinfo

import com.example.mealsapp.domain.model.Meal

data class MealInfoState(
    val meal: Meal? = Meal(),
    val isLoading: Boolean=true,
    val error: String? = null
)
