package com.example.mealsapp.presenation.search

import com.example.mealsapp.domain.model.Meal

data class SearchState(
    val mealList: List<Meal>? = emptyList(),
    val query:String =""
)
