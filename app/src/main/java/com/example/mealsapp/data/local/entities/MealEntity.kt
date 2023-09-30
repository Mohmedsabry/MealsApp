package com.example.mealsapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mealTB")
data class MealEntity(
    val idMeal: String="",
    val strMeal: String="",
    val strMealThumb: String="",
    val strCategory: String="",
    val strInstructions: String="",
    val strArea: String="",
    val fav: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)
