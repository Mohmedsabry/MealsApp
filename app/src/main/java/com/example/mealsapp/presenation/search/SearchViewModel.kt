package com.example.mealsapp.presenation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealsapp.domain.repository.Repository
import com.example.mealsapp.util.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    var state by mutableStateOf(SearchState())
    private var job: Job? = null
    private fun applySearch(
        fetchFromApi: Boolean = false
    ) {
        viewModelScope.launch {
            val result = repository.getMealByName(fetchFromApi, state.query)
            state = when (result) {
                is Resources.Error -> {
                    state.copy(
                        mealList = null
                    )
                }

                is Resources.Loading -> TODO()
                is Resources.Success -> {
                    state.copy(
                        mealList = result.data
                    )
                }
            }
        }
    }

    init {
        applySearch()
    }

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnTyping -> {
                state = state.copy(query = event.query)
                job?.cancel()
                job = viewModelScope.launch {
                    delay(500L)
                    applySearch()
                }
            }
        }
    }
}