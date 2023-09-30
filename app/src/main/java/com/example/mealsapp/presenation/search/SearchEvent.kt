package com.example.mealsapp.presenation.search

sealed class SearchEvent {
    data class OnTyping(val query: String) : SearchEvent()
}
