package com.example.testapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.testapp.data.api.models.ProductResponseModel
import com.google.gson.Gson
import org.mockito.Mockito
import java.io.File
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

object TestUtils {

    fun getJson(path: String): String {
        val uri = this.javaClass.classLoader?.getResource(path)
        return uri?.let {
            val file = File(it.path)
            String(file.readBytes())
        }.orEmpty()
    }

    fun getProductResponseModel(): ProductResponseModel {
        return Gson().fromJson(getJson("product.json"), ProductResponseModel::class.java)
    }

    fun makeValidPath(path: String): String {
        return "/$path"
    }

    fun <T> mAnyObject(): T {
        return Mockito.any<T>()
    }
}

fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }

    this.observeForever(observer)

    // Don't wait indefinitely if the LiveData is not set.
    if (!latch.await(time, timeUnit)) {
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}
