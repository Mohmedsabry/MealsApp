package com.example.mealsapp.data.mapper

import com.example.mealsapp.data.local.entities.CategoryEntity
import com.example.mealsapp.data.remote.dto.CategoryDto
import com.example.mealsapp.data.remote.dto.CategoryListDto
import com.example.mealsapp.domain.model.Category

fun CategoryListDto.toCategoryList(): List<Category> {
    return this.categories.map {
        it.toCategory()
    }
}

fun CategoryDto.toCategory(): Category {
    return Category(
        idCategory, strCategory, strCategoryDescription, strCategoryThumb
    )
}

fun CategoryEntity.toCategory(): Category {
    return Category(
        idCategory, strCategory, strCategoryDescription, strCategoryThumb
    )
}

fun CategoryDto.toCategoryEntity(): CategoryEntity {
    return CategoryEntity(
        idCategory, strCategory, strCategoryDescription, strCategoryThumb
    )
}