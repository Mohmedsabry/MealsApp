package com.example.mealsapp.domain.repository

import com.example.mealsapp.domain.model.Category
import com.example.mealsapp.domain.model.Meal
import com.example.mealsapp.domain.model.RandomMeal
import com.example.mealsapp.util.Resources
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getRandomMeal(): Flow<Resources<RandomMeal>>

    suspend fun getCategories(
        fromApi: Boolean,
    ): Resources<List<Category>>

    suspend fun getMealById(
        fromApi: Boolean,
        id: String
    ): Resources<Meal>

    suspend fun getMealByName(
        fromApi: Boolean,
        name: String
    ): Resources<List<Meal>>

    suspend fun getMealsCategory(
        fromApi: Boolean,
        categoryName: String
    ): Flow<Resources<List<Meal>>>

    suspend fun updateFavourite(
        id: String,
        state: Boolean
    )

    suspend fun getAllFavourites(

    ): List<Meal>
}