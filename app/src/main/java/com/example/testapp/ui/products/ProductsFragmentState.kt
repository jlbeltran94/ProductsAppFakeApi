package com.example.testapp.ui.products

import androidx.paging.PagingData
import com.example.testapp.ui.models.Product

sealed class ProductsFragmentState {
    object Loading : ProductsFragmentState()
    data class Result(val data: PagingData<Product>) : ProductsFragmentState()
}