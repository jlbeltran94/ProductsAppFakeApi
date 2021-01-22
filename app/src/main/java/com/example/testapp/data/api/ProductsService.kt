package com.example.testapp.data.api

import com.example.testapp.data.api.Endpoints.GET_ALL_PRODUCTS
import com.example.testapp.data.api.Endpoints.GET_PRODUCT
import com.example.testapp.data.api.Endpoints.PRODUCT_ID
import com.example.testapp.data.api.models.ProductResponseModel
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductsService {

    @GET(GET_ALL_PRODUCTS)
    suspend fun getProducts(): List<ProductResponseModel>

    @GET(GET_PRODUCT)
    suspend fun getProductById(@Path(PRODUCT_ID) productId: Int): ProductResponseModel

}