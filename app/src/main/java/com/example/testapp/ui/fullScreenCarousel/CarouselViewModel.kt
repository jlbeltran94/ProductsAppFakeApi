package com.example.testapp.ui.fullScreenCarousel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.testapp.ui.navigation.NavigationDispatcher
import com.example.testapp.ui.productDetail.ProductDetailFragment.Companion.CURRENT_PAGE

class CarouselViewModel @ViewModelInject constructor(
    private val navigationDispatcher: NavigationDispatcher
) : ViewModel() {

    fun setCurrentPage(page: Int) {
        navigationDispatcher.emit {
            it.previousBackStackEntry?.savedStateHandle?.set(CURRENT_PAGE, page)
        }
    }
}