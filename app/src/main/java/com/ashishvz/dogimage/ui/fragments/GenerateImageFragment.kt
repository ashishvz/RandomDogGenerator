package com.ashishvz.dogimage.ui.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ashishvz.dogimage.R
import com.ashishvz.dogimage.databinding.FragmentGenerateImageBinding
import com.ashishvz.dogimage.viewmodel.GenerateImageViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class GenerateImageFragment : Fragment() {
    private lateinit var binding: FragmentGenerateImageBinding
    private val viewModel: GenerateImageViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_generate_image, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            generateButton.setOnClickListener {
                viewModel.generateImage()
            }
        }
        collectUiState()
    }

    private fun collectUiState() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.distinctUntilChanged().collectLatest {
                    binding.apply {
                        Glide.with(requireActivity()).load(it.imgUrl)
                            .listener(object : RequestListener<Drawable> {
                                override fun onLoadFailed(
                                    e: GlideException?,
                                    model: Any?,
                                    target: Target<Drawable>?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    contentProgressBar.isVisible = it.isLoading
                                    generateText.isVisible = it.noImage
                                    generateButton.isEnabled = it.isButtonEnabled
                                    return false
                                }

                                override fun onResourceReady(
                                    resource: Drawable?,
                                    model: Any?,
                                    target: Target<Drawable>?,
                                    dataSource: DataSource?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    contentProgressBar.isVisible = it.isLoading
                                    generateText.isVisible = it.noImage
                                    generateButton.isEnabled = it.isButtonEnabled
                                    return false
                                }

                            }).into(dogImageView)
                    }
                }
            }
        }
    }
}