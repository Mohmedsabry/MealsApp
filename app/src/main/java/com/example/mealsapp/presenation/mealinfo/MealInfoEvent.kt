package com.example.mealsapp.presenation.mealinfo

sealed class MealInfoEvent{
    data object IsRefresh:MealInfoEvent()
    data object OnClickedFavourite:MealInfoEvent()
}
