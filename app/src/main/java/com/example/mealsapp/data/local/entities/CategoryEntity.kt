package com.example.mealsapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categoryTB")
data class CategoryEntity(
    val idCategory: String,
    val strCategory: String,
    val strCategoryDescription: String,
    val strCategoryThumb: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)
