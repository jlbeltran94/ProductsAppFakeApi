package com.example.testapp.domain.mappers

import com.example.testapp.data.api.models.ProductResponseModel
import com.example.testapp.ui.models.Product
import javax.inject.Inject

class ProductMapper @Inject constructor() {

    fun productFromResponseModels(
        productResponseModel: ProductResponseModel
    ): Product {
        return Product(
            id = productResponseModel.id,
            title = productResponseModel.title,
            price = productResponseModel.price,
            description = productResponseModel.description,
            image = productResponseModel.image
        )
    }

    fun productsFromResponseModels(
        productResponseModels: List<ProductResponseModel>
    ): List<Product> {
        return productResponseModels.map { productResponseModel ->
            Product(
                id = productResponseModel.id,
                title = productResponseModel.title,
                price = productResponseModel.price,
                description = productResponseModel.description,
                image = productResponseModel.image
            )
        }
    }
}