package com.example.testapp.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.testapp.R
import com.example.testapp.databinding.ItemCarouselImageBinding
import com.example.testapp.inflate

class ImagesAdapter : RecyclerView.Adapter<ImageViewHolder>() {
    var listener: (() -> Unit)? = null
    var data = listOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(parent.inflate(R.layout.item_carousel_image))
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(data[position], listener)
    }

    override fun getItemCount(): Int = data.size

}

class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemCarouselImageBinding.bind(itemView)
    fun bind(url: String, listener: (() -> Unit)?) {
        binding.image.load(url)
        binding.root.setOnClickListener {
            listener?.invoke()
        }
    }
}