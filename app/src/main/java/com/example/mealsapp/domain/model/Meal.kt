package com.example.mealsapp.domain.model

data class Meal(
    val idMeal: String? = "",
    val strMeal: String? = "",
    val strMealThumb: String? = "",
    val strCategory: String? = "",
    val strInstructions: String? = "",
    val strTags: String? = "",
    val strArea: String? = "",
    val fav:Boolean? = false
)
