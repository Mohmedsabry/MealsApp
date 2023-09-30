package com.example.mealsapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mealsapp.data.local.entities.CategoryEntity
import com.example.mealsapp.data.local.entities.MealEntity

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categoryList: List<CategoryEntity>)

    @Query("delete from categoryTB")
    suspend fun deleteAllCategories()

    @Query("select * from categoryTB")
    suspend fun getAllCategories(): List<CategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: MealEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMeals(meals: List<MealEntity>)


    @Query("select * from mealTB where idMeal == :id")
    suspend fun getMealById(id: String): MealEntity

    @Query("select * from mealTB where lower(strMeal) like lower(:query)||'%'")
    suspend fun getMealsByName(query: String): List<MealEntity>

    @Query("select * from mealTB where lower(strCategory) == lower(:query)")
    suspend fun getMealsByCategory(query: String): List<MealEntity>


    @Query("select * from mealTB")
    suspend fun getAllMeals(): List<MealEntity>

    @Query("delete from mealTB where strCategory =:categoryName")
    suspend fun deleteAllMealsCategory(categoryName: String)

    @Query("delete from mealTB")
    suspend fun deleteAllMeals()
    @Query("update mealTB set strInstructions = :strInstructions , strArea = :strArea where idMeal ==:id ")
    suspend fun updateMeal(id:String,strInstructions:String,strArea:String)

    @Query("update mealTB set fav =:state where idMeal == :id")
    suspend fun updateFavourite(id: String, state: Boolean)

    @Query("select * from mealTB where fav = 1")
    suspend fun getFavourites(): List<MealEntity>

}