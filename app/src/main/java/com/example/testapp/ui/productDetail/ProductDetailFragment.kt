package com.example.testapp.ui.productDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.testapp.R
import com.example.testapp.databinding.FragmentProductDetailBinding
import com.example.testapp.getSavedStateLiveData
import com.example.testapp.observe
import com.example.testapp.utils.Event
import com.example.testapp.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    lateinit var binding: FragmentProductDetailBinding
    private val productDetailViewModel: ProductDetailViewModel by viewModels()
    private val args: ProductDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListeners()
        setupViewModel()
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            productDetailViewModel.navigateBack()
        }
    }

    private fun setupViewModel() {
        observe(productDetailViewModel.productDetailState) {
            handleState(it)
        }
        observe(getSavedStateLiveData<Int>(CURRENT_PAGE)) {
            binding.imageCarousel.setPage(it)
        }
        if (productDetailViewModel.productDetailState.value?.peekContent() !is ProductDetailFragmentState.Result) {
            productDetailViewModel.searchProduct(args.productId)
        }
    }

    private fun handleState(state: Event<ProductDetailFragmentState>) {
        when (val content = state.peekContent()) {
            is ProductDetailFragmentState.Result -> {
                binding.progressBar.visible(false)
                binding.detailGroup.visible(true)
                binding.errorView.visible(false)
                binding.productTitle.text = content.data.title
                binding.productDescription.text = content.data.description
                binding.productPrice.text = content.data.price
                binding.imageCarousel.bindImages(listOf(content.data.image)) {
                    productDetailViewModel.navToFullScreenCarousel(
                        listOf(content.data.image),
                        binding.imageCarousel.currentPage
                    )
                }
            }
            is ProductDetailFragmentState.Loading -> {
                binding.progressBar.visible(true)
                binding.errorView.visible(false)
                binding.detailGroup.visible(false)
            }
            is ProductDetailFragmentState.Error -> {
                binding.errorView.visible(true)
                binding.detailGroup.visible(false)
                binding.progressBar.visible(false)
                binding.errorView.errorMessage.text =
                    resources.getString(
                        R.string.error_message,
                        content.error.localizedMessage.orEmpty()
                    )
                binding.errorView.buttonRetry.setOnClickListener {
                    productDetailViewModel.searchProduct(args.productId)
                }
            }
        }
    }

    companion object {
        const val CURRENT_PAGE = "CURRENT_PAGE"
    }
}