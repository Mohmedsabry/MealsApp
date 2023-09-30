package com.example.mealsapp.presenation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealsapp.domain.model.RandomMeal
import com.example.mealsapp.domain.repository.Repository
import com.example.mealsapp.util.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    var state by mutableStateOf(HomeState())
    private fun showRandomMeal() {
        viewModelScope.launch {
            repository.getRandomMeal().collect { result ->
                when (result) {
                    is Resources.Error -> {
                        state = state.copy(
                            errorMassage = result.message,
                            isLoading = false,
                            randomMeal = RandomMeal(strMealThumb = null)
                        )

                    }

                    is Resources.Loading -> {
                        state = state.copy(
                            isLoading = result.isLoading,
                            errorMassage = null,
                        )

                    }

                    is Resources.Success -> {
                        state = state.copy(
                            randomMeal = result.data ?: RandomMeal(),
                            isLoading = false,
                            errorMassage = null
                        )
                    }
                }
            }
        }
    }

    private fun getCategories(fetchFromApi: Boolean = false) {
        viewModelScope.launch {
            val result = async {
                repository.getCategories(fetchFromApi)
            }
            when (val data = result.await()) {
                is Resources.Error -> {
                    state = state.copy(
                        categoryList = null,
                        errorMassage = data.message
                    )
                }

                is Resources.Loading -> {
                    state = state.copy(
                        errorMassage = null,
                        categoryList = null
                    )
                }

                is Resources.Success -> {
                    state = state.copy(
                        errorMassage = null,
                        categoryList = data.data
                    )
                }
            }
        }
    }

    fun onEvent(events: HomeEvents) {
        when (events) {
            HomeEvents.IsRefreshing -> {
                showRandomMeal()
                getCategories(true)
            }
        }
    }

    init {
        showRandomMeal()
        getCategories()
    }
}