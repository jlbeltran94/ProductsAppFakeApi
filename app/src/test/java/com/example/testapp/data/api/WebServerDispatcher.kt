package com.example.testapp.data.api

import com.example.testapp.TestUtils
import com.example.testapp.TestUtils.makeValidPath
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

object WebServerDispatcher : Dispatcher() {
    @Throws(InterruptedException::class)
    override fun dispatch(request: RecordedRequest): MockResponse {
        return when (request.path) {
            makeValidPath(Endpoints.GET_ALL_PRODUCTS)-> {
                MockResponse()
                    .setResponseCode(200)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody(TestUtils.getJson("products.json"))
            }
            "/products/1" -> {
                MockResponse()
                    .setResponseCode(200)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody(TestUtils.getJson("product.json"))
            }
            "/items/test/descriptions/test-1" -> {
                MockResponse()
                    .setResponseCode(200)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody(TestUtils.getJson("description.json"))
            }
            else -> MockResponse().setResponseCode(404)
        }
    }
}