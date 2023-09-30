package com.example.mealsapp.data.mapper

import com.example.mealsapp.data.local.entities.MealEntity
import com.example.mealsapp.data.remote.dto.MealDto
import com.example.mealsapp.data.remote.dto.MealsDto
import com.example.mealsapp.domain.model.Meal

fun MealsDto.toListMeal(): List<Meal> {
    return this.meals.map {
        it.toMeal()
    }
}

fun MealDto.toMeal(): Meal {
    return Meal(
        idMeal, strMeal, strMealThumb, strCategory, strInstructions, strTags
    )
}

fun MealEntity.toMeal(): Meal {
    return Meal(
        idMeal = idMeal ?: "",
        strMeal = strMeal ?: "",
        strMealThumb = strMealThumb ?: "",
        strCategory = strCategory ?: "",
        strInstructions = strInstructions ?: "",
        strArea = strArea ?: "",
        fav = fav ?: false
    )
}

fun MealDto.toMealEntity(): MealEntity {
    return MealEntity(
        idMeal, strMeal, strMealThumb, strCategory, strInstructions, strArea
    )
}