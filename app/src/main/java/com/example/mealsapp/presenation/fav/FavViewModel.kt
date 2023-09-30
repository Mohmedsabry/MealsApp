package com.example.mealsapp.presenation.fav

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealsapp.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    var state by mutableStateOf(FavState())
    private fun getAllFav() {
        viewModelScope.launch {
            val result = repository.getAllFavourites()
            state = state.copy(mealList = result)
        }
    }

    init {
        getAllFav()
    }
}