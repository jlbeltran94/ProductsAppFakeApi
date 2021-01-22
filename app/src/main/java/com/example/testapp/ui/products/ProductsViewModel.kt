package com.example.testapp.ui.products

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.testapp.domain.interactors.ProductsInteractor
import com.example.testapp.ui.navigation.NavigationDispatcher
import com.example.testapp.utils.Event
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProductsViewModel @ViewModelInject constructor(
    private val productsInteractor: ProductsInteractor,
    private val navigationDispatcher: NavigationDispatcher
) : ViewModel() {

    private val _productsFragmentState: MutableLiveData<Event<ProductsFragmentState>> =
        MutableLiveData<Event<ProductsFragmentState>>()
    val productsFragmentState: LiveData<Event<ProductsFragmentState>> = _productsFragmentState

    fun navToProductDetail(productId: Int) {
        navigationDispatcher.emit {
            val action =
                ProductsFragmentDirections.actionProductsFragmentToProductDetailFragment(productId)
            it.navigate(action)
        }
    }

    fun searchPagedProducts() {
        viewModelScope.launch {
            _productsFragmentState.postValue(Event(ProductsFragmentState.Loading))
            productsInteractor.getPagedProducts().cachedIn(viewModelScope).collectLatest {
                _productsFragmentState.postValue(Event(ProductsFragmentState.Result(it)))
            }
        }
    }
}