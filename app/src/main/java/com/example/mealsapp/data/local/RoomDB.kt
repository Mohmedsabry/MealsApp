package com.example.mealsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mealsapp.data.local.entities.CategoryEntity
import com.example.mealsapp.data.local.entities.MealEntity

@Database(entities = [MealEntity::class, CategoryEntity::class], version = 1)
abstract class RoomDB : RoomDatabase() {
    abstract val dao: Dao
}