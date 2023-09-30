package com.example.mealsapp.presenation.categoryInfo

import com.example.mealsapp.domain.model.Meal

data class CategoryInfoState(
    val mealList: List<Meal>? = emptyList(),
    val isLoading:Boolean = true,
    val errorMessage:String? = null
)
