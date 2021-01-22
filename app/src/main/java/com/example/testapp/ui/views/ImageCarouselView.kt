package com.example.testapp.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.example.testapp.R
import com.example.testapp.databinding.ItemCarouselImageBinding
import com.example.testapp.databinding.ViewImageCarouselBinding
import com.example.testapp.ui.adapters.ImagesAdapter

class ImageCarouselView(context: Context, attrs: AttributeSet? = null) :
    ConstraintLayout(context, attrs) {

    private var binding: ViewImageCarouselBinding =
        ViewImageCarouselBinding.inflate(LayoutInflater.from(context), this)
    private val imagesAdapter by lazy { ImagesAdapter() }
    val currentPage: Int
        get() = binding.imagePager.currentItem

    init {
        binding.imagePager.adapter = imagesAdapter
    }

    fun bindImages(images: List<String>, setCurrentPage: Int = 0, listener: (() -> Unit)? = null) {
        imagesAdapter.data = images
        imagesAdapter.listener = listener
        binding.imagesCount.text = resources.getString(R.string.pages_counter,1, images.size)
        binding.imagePager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val page = position + 1
                binding.imagesCount.text = resources.getString(R.string.pages_counter,page, images.size)
            }
        })
        binding.imagePager.setCurrentItem(setCurrentPage, false)
    }

    fun setPage(page: Int) {
        binding.imagePager.setCurrentItem(page, false)
    }
}
