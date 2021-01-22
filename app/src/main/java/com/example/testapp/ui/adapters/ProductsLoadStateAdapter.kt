package com.example.testapp.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.databinding.LoadStateViewBinding
import com.example.testapp.inflate
import com.example.testapp.visible

class ProductsLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoadStateViewHolder>() {
    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState, retry)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(parent.inflate(R.layout.load_state_view))
    }
}

class LoadStateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = LoadStateViewBinding.bind(itemView)
    fun bind(loadState: LoadState, retry: () -> Unit) {
        binding.btnRetry.visible(loadState !is LoadState.Loading)
        binding.errorMessage.visible(loadState !is LoadState.Loading)
        binding.progressBar.visible(loadState is LoadState.Loading)
        if (loadState is LoadState.Error) {
            binding.errorMessage.text = loadState.error.localizedMessage
        }
        binding.btnRetry.setOnClickListener {
            retry.invoke()
        }
    }
}