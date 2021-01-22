package com.example.testapp.ui

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.testapp.R
import com.example.testapp.data.providers.RecentSearchProvider
import com.example.testapp.databinding.ActivityMainBinding
import com.example.testapp.ui.navigation.NavigationDispatcher
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigationDispatcher: NavigationDispatcher

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navigationDispatcher.navigationCommands.observe(this) { event ->
            event.getContentIfNotHandled()?.invoke(findNavController(R.id.nav_host_fragment))
        }
        handleSearchIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleSearchIntent(intent)
    }

    private fun handleSearchIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.let { query ->
                SearchRecentSuggestions(
                    this,
                    RecentSearchProvider.AUTHORITY,
                    RecentSearchProvider.MODE
                ).saveRecentQuery(query, null)
                mainViewModel.launchSearchProducts(query)
            }
        }
    }
}