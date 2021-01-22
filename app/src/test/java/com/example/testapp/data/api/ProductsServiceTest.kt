package com.example.testapp.data.api

import com.example.testapp.TestUtils
import com.google.gson.GsonBuilder
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductsServiceTest {

    @get:Rule
    val mockServer by lazy { MockWebServer() }
    private lateinit var productsService: ProductsService

    @Before
    fun setup() {
        productsService = Retrofit.Builder()
            .baseUrl(mockServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(ProductsService::class.java)
        mockServer.dispatcher = WebServerDispatcher
    }

    @Test
    fun `verify product is obtained`() {
        val productId = 1
        val productsResponse = runBlocking { productsService.getProductById(productId) }
        val takeRequest = mockServer.takeRequest()
        Assert.assertEquals(TestUtils.getProductResponseModel(), productsResponse)
        Assert.assertEquals(
            TestUtils.makeValidPath("items/1?attributes=id,title,price,pictures,descriptions"),
            takeRequest.path
        )
        Assert.assertEquals(
            "GET",
            takeRequest.method
        )
    }

}