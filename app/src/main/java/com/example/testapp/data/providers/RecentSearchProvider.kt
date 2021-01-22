package com.example.testapp.data.providers

import android.content.SearchRecentSuggestionsProvider

class RecentSearchProvider : SearchRecentSuggestionsProvider() {
    init {
        setupSuggestions(AUTHORITY, MODE)
    }

    companion object {
        const val AUTHORITY = "com.example.RecentSearchProvider"
        const val MODE: Int = DATABASE_MODE_QUERIES
    }
}