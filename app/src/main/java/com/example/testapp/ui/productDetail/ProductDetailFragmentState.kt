package com.example.testapp.ui.productDetail

import com.example.testapp.ui.models.Product

sealed class ProductDetailFragmentState {
    object Loading : ProductDetailFragmentState()
    data class Result(val data: Product) : ProductDetailFragmentState()
    data class Error(val error: Throwable) : ProductDetailFragmentState()
}