package com.example.testapp.ui.products

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.filter
import com.example.testapp.R
import com.example.testapp.databinding.FragmentProductsBinding
import com.example.testapp.ui.adapters.ProductsLoadStateAdapter
import com.example.testapp.ui.adapters.ProductsPagingAdapter
import com.example.testapp.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    lateinit var binding: FragmentProductsBinding
    private val productsViewModel: ProductsViewModel by viewModels()
    private val args: ProductsFragmentArgs by navArgs()
    private val pagingAdapter by lazy { ProductsPagingAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupSearchView()
        setupPagingAdapter()
        setupRecycler()
        setupViewModel()
    }

    private fun setupViewModel() {
        productsViewModel.searchPagedProducts()
        productsViewModel.productsFragmentState.observe(viewLifecycleOwner) {
            handleState(it.getContentIfNotHandled())
        }
    }

    private fun setupSearchView() {
        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        binding.searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            queryHint = resources.getString(R.string.query_hint_search)
            isQueryRefinementEnabled = true
        }
    }

    private fun setupRecycler() {
        binding.productsRecycler.apply {
            setHasFixedSize(true)
            adapter = pagingAdapter.withLoadStateHeaderAndFooter(
                ProductsLoadStateAdapter { pagingAdapter.retry() },
                ProductsLoadStateAdapter { pagingAdapter.retry() }
            )
        }
    }

    private fun setupPagingAdapter() {
        pagingAdapter.onClick = {
            productsViewModel.navToProductDetail(it)
        }
        pagingAdapter.addLoadStateListener {
            handlePagingState(it)
        }
    }

    private fun handleState(state: ProductsFragmentState?) {
        args.query?.let {
            binding.searchView.setQuery(it, false)
        }
        when (state) {
            is ProductsFragmentState.Loading -> {
                binding.progressBar.visible()
            }
            is ProductsFragmentState.Result -> {
                pagingAdapter.submitData(
                    lifecycle,
                    state.data.filter { it.title.contains(args.query ?: "") })
            }
        }
    }

    private fun handlePagingState(loadState: CombinedLoadStates) {
        when {
            loadState.refresh is LoadState.Loading -> {
                binding.progressBar.visible(pagingAdapter.itemCount == 0)
                binding.emptyView.visible(false)
                binding.errorView.visible(false)
            }
            loadState.append.endOfPaginationReached -> {
                binding.progressBar.visible(false)
                binding.emptyView.visible(pagingAdapter.itemCount == 0)
                binding.errorView.visible(false)
            }
            else -> {
                binding.progressBar.visible(false)
                binding.emptyView.visible(false)
                // getting the error
                val errorState = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let { err ->
                    binding.errorView.visible(pagingAdapter.itemCount == 0)
                    binding.errorView.errorMessage.text = resources.getString(
                        R.string.error_message,
                        err.error.localizedMessage.orEmpty()
                    )
                    binding.errorView.buttonRetry.setOnClickListener {
                        pagingAdapter.retry()
                    }
                }
            }
        }
    }
}