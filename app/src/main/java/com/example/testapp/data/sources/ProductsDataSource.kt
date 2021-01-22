package com.example.testapp.data.sources

import androidx.paging.PagingSource
import com.example.testapp.domain.interactors.ProductsInteractor
import com.example.testapp.ui.models.Product

class ProductsDataSource(
    private val productsInteractor: ProductsInteractor
) : PagingSource<Int, Product>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        return try {
            val nextOffset = params.key ?: 0
            val data = productsInteractor.getProducts()
            LoadResult.Page(
                data = data,
                prevKey = null,
                nextKey = null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}