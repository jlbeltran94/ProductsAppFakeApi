package com.example.testapp.ui.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.testapp.R
import com.example.testapp.databinding.ItemProductBinding
import com.example.testapp.inflate
import com.example.testapp.ui.models.Product


class ProductsPagingAdapter :
    PagingDataAdapter<Product, ProductViewHolder>(ProductComparator) {
    var onClick: ((Int) -> Unit)? = null
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, onClick) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(parent.inflate(R.layout.item_product))
    }
}

object ProductComparator : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}

class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemProductBinding.bind(itemView)

    fun bind(product: Product, onClick: ((Int) -> Unit)?) {
        binding.root.setOnClickListener {
            onClick?.invoke(product.id)
        }
        binding.productTitle.text = product.title
        binding.productPrice.text = product.price
        binding.productImage.load(product.image)
    }

}