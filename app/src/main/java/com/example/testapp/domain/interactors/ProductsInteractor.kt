package com.example.testapp.domain.interactors

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.testapp.data.api.ProductsService
import com.example.testapp.data.sources.ProductsDataSource
import com.example.testapp.domain.mappers.ProductMapper
import com.example.testapp.ui.models.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductsInteractor @Inject constructor(
    private val productsService: ProductsService,
    private val productMapper: ProductMapper
) {

    suspend fun getProducts(): List<Product> {
        val searchResponse = productsService.getProducts()
        return productMapper.productsFromResponseModels(searchResponse)
    }

    fun getPagedProducts(): Flow<PagingData<Product>> =
        Pager(PagingConfig(pageSize = 50)) {
            ProductsDataSource( this)
        }.flow

    suspend fun getProduct(productId: Int): Product {
        val productResponse = productsService.getProductById(productId)
        return productMapper.productFromResponseModels(productResponse)
    }


}