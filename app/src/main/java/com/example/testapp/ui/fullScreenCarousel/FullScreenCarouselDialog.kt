package com.example.testapp.ui.fullScreenCarousel

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.testapp.R
import com.example.testapp.databinding.DialogFullScreenCarouselBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FullScreenCarouselDialog : DialogFragment() {

    private lateinit var binding: DialogFullScreenCarouselBinding
    private val carouselViewModel: CarouselViewModel by viewModels()
    private val args: FullScreenCarouselDialogArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme_FullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFullScreenCarouselBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            it.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.carouselFullScreen.bindImages(args.images.toList(), args.currentImage)
        binding.imageBack.setOnClickListener {
            carouselViewModel.setCurrentPage(binding.carouselFullScreen.currentPage)
            dismiss()
        }
    }
}