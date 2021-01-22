package com.example.testapp.ui.productDetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.domain.interactors.ProductsInteractor
import com.example.testapp.ui.navigation.NavigationDispatcher
import com.example.testapp.utils.Event
import kotlinx.coroutines.launch

class ProductDetailViewModel @ViewModelInject constructor(
    private val productsInteractor: ProductsInteractor,
    private val navigationDispatcher: NavigationDispatcher
) : ViewModel() {

    private val _productDetailState: MutableLiveData<Event<ProductDetailFragmentState>> =
        MutableLiveData<Event<ProductDetailFragmentState>>()
    val productDetailState: LiveData<Event<ProductDetailFragmentState>> = _productDetailState

    fun navigateBack() {
        navigationDispatcher.emit {
            it.popBackStack()
        }
    }

    fun navToFullScreenCarousel(images: List<String>, currentImage: Int = 0) {
        navigationDispatcher.emit {
            val action =
                ProductDetailFragmentDirections.actionProductDetailFragmentToFullScreenCarouselFragment(
                    images.toTypedArray(),
                    currentImage
                )
            it.navigate(action)
        }
    }

    fun searchProduct(productId: Int) {
        viewModelScope.launch {
            try {
                _productDetailState.postValue(Event(ProductDetailFragmentState.Loading))
                val product = productsInteractor.getProduct(productId)
                _productDetailState.value = Event(ProductDetailFragmentState.Result(product))
            } catch (e: Exception) {
                _productDetailState.postValue(Event(ProductDetailFragmentState.Error(e)))
            }
        }
    }
}