package com.example.testapp.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.testapp.R
import com.example.testapp.ui.navigation.NavigationDispatcher
import com.example.testapp.ui.products.ProductsFragmentDirections

class MainViewModel @ViewModelInject constructor(
    private val navigationDispatcher: NavigationDispatcher
) : ViewModel() {

    fun launchSearchProducts(query: String) {
        navigationDispatcher.emit { navController ->
            val action = when (navController.currentDestination?.id) {
                R.id.productsFragment -> {
                    ProductsFragmentDirections.actionProductsFragmentSelf(query)
                }
                else -> null
            }
            action?.let { navController.navigate(it) }
        }
    }
}