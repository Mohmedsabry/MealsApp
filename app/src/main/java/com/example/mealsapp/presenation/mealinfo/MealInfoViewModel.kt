package com.example.mealsapp.presenation.mealinfo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealsapp.domain.model.Meal
import com.example.mealsapp.domain.repository.Repository
import com.example.mealsapp.util.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealInfoViewModel @Inject constructor(
    private val repo: Repository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    var state by mutableStateOf(MealInfoState())
    fun onEvent(event: MealInfoEvent) {
        when (event) {
            MealInfoEvent.IsRefresh -> {
                getMealById()
            }

            MealInfoEvent.OnClickedFavourite -> {
                val stateBoolean = state.meal?.fav
                state = state.copy(meal = state.meal?.copy(fav = stateBoolean?.not()))
                viewModelScope.launch {
                    repo.updateFavourite(
                        state.meal?.idMeal ?: "",
                        state.meal?.fav ?: false
                    )
                }
            }
        }
    }

    private fun getMealById(
        fetchFromApi: Boolean = false
    ) {
        viewModelScope.launch {
            val id = savedStateHandle.get<String>("idMeal") ?: ""
            val result = async { repo.getMealById(fetchFromApi, id) }
            when (val data = result.await()) {
                is Resources.Error -> {

                    state = state.copy(
                        meal = Meal(),
                        isLoading = false,
                        error = data.message
                    )
                }

                is Resources.Loading -> {

                    state = state.copy(
                        isLoading = data.isLoading,
                        meal = Meal(),
                        error = null
                    )
                }

                is Resources.Success -> {

                    state = state.copy(
                        meal = data.data ?: Meal(),
                        error = null,
                        isLoading = false
                    )
                }
            }
        }
    }

    init {
        getMealById()
        viewModelScope.launch {
            delay(1000)
            onEvent(MealInfoEvent.IsRefresh)
        }
    }
}