package com.example.mealsapp.data.remote

import com.example.mealsapp.data.remote.dto.CategoryListDto
import com.example.mealsapp.data.remote.dto.MealsDto
import com.example.mealsapp.data.remote.dto.RandomMealDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("random.php")
    suspend fun getRandomMeal(): RandomMealDto

    @GET("categories.php")
    suspend fun getCategories(

    ): CategoryListDto

    @GET("search.php")
    suspend fun getMealByName(
        @Query("s") name: String
    ): MealsDto

    @GET("lookup.php")
    suspend fun getMealById(
        @Query("i") id: String
    ): MealsDto


    @GET("filter.php")
    suspend fun getCategoryMeals(
        @Query("c") query: String
    ): MealsDto

    companion object {
        const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"
    }
}