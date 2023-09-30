package com.example.mealsapp.presenation.categoryInfo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealsapp.domain.repository.Repository
import com.example.mealsapp.util.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryInfoViewModel @Inject constructor(
    private val repository: Repository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    var state by
    mutableStateOf(CategoryInfoState())

    private fun getAllCategoryMeals(
        fetchFromApi: Boolean = false
    ) {
        viewModelScope.launch {
            val categoryName = savedStateHandle.get<String>("strCategory")
            val result =
                repository.getMealsCategory(fromApi = fetchFromApi, categoryName ?: "")
            result.collect { resultResource ->
                when (resultResource) {
                    is Resources.Error -> {
                        state = state.copy(
                            mealList = null,
                            isLoading = false,
                            errorMessage = resultResource.message
                        )
                    }
                    is Resources.Loading -> {
                        state = state.copy(
                            isLoading = resultResource.isLoading,
                            errorMessage = null
                        )
                    }

                    is Resources.Success -> {
                        state = state.copy(
                            mealList = resultResource.data,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
            }
        }
    }

    init {
        getAllCategoryMeals()
    }
}