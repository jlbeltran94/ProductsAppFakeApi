package com.example.testapp.domain.interactors

import com.example.testapp.TestUtils
import com.example.testapp.data.api.ProductsService
import com.example.testapp.domain.mappers.ProductMapper
import com.example.testapp.ui.models.Product
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ProductsInteractorTest {

    private val productResponseModel = TestUtils.getProductResponseModel()
    private val expectedProduct =
        with(productResponseModel) { Product(id, title, price, "", "") }
    private val expectedProductListItem = listOf(expectedProduct)


    private val productsService: ProductsService = mock {
        onBlocking { getProductById(productResponseModel.id) } doReturn productResponseModel
    }

    private val productMapper: ProductMapper = mock {
        on {
            productFromResponseModels(
                productResponseModel
            )
        } doReturn expectedProduct
    }

    lateinit var productsInteractor: ProductsInteractor

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        productsInteractor = ProductsInteractor(productsService, productMapper)
    }

    @Test
    fun `verify products are obtained when search`() {
        val searchProductsResult = runBlocking { productsInteractor.getProducts() }
        assert(searchProductsResult.isNotEmpty())
        assertEquals(expectedProductListItem, searchProductsResult.first())
    }

    @Test
    fun `verify product is obtained by id`() {
        val product = runBlocking { productsInteractor.getProduct(productResponseModel.id) }
        assertEquals(expectedProduct, product)
    }

    companion object {
        private const val QUERY = "test"
    }
}