package com.example.mealsapp.data.mapper

import com.example.mealsapp.data.remote.dto.MealDto
import com.example.mealsapp.data.remote.dto.RandomMealDto
import com.example.mealsapp.domain.model.RandomMeal

fun RandomMealDto.toRandomMeal(): RandomMeal {
    val meal = this.meals.getOrNull(0) ?: MealDto()
    return RandomMeal(
        idMeal = meal.idMeal,
        strMeal = meal.strMeal,
        strMealThumb = meal.strMealThumb,
        strCategory = meal.strCategory,
        strInstructions = meal.strInstructions,
        strArea = meal.strArea
    )
}

