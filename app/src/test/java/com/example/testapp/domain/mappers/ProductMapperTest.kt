package com.example.testapp.domain.mappers

import com.example.testapp.TestUtils
import com.example.testapp.ui.models.Product
import org.junit.Test

import org.junit.Assert.*

class ProductMapperTest {

    private val productMapper: ProductMapper = ProductMapper()
    private val productResponseModel = TestUtils.getProductResponseModel()


    @Test
    fun `verify the product is mapped correctly with description when exists`() {
        val product =
            productMapper.productFromResponseModels(
                productResponseModel
            )
        assertEquals(productResponseModel.id, product.id)
        assertEquals(productResponseModel.title, product.title)
        assertEquals(productResponseModel.price, product.price)
        assertEquals(
            productResponseModel.image.firstOrNull(),
            product.image.firstOrNull()
        )
        assertEquals(productResponseModel.description, product.description)
    }

    @Test
    fun `verify the product is mapped correctly without images when do not exist`() {
        val product =
            productMapper.productFromResponseModels(
                productResponseModel.copy(image = "")
            )
        assertEquals(productResponseModel.id, product.id)
        assertEquals(productResponseModel.title, product.title)
        assertEquals(productResponseModel.price, product.price)
        assert(product.image.isEmpty())
        assertEquals("", product.description)
    }

    @Test
    fun `verify the products list items are mapped correctly`() {
        val expectedProductListItem = with(productResponseModel) {
            Product(
                id = id,
                title = title,
                price = price,
                image = image,
                description = description
            )
        }
        val products = productMapper.productsFromResponseModels(listOf(productResponseModel))
        assert(products.isNotEmpty())
        assertEquals(expectedProductListItem, products.first())
    }
}