package com.ashishvz.dogimage.ui.fragments

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.ashishvz.dogimage.R
import com.ashishvz.dogimage.databinding.FragmentGetPreviousImagesBinding
import com.ashishvz.dogimage.ui.adapters.DogImageAdapter
import com.ashishvz.dogimage.viewmodel.LocalImageViewmodel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class GetPreviousImageFragment : Fragment() {

    private lateinit var binding: FragmentGetPreviousImagesBinding
    private lateinit var dogImageAdapter: DogImageAdapter
    private val viewModel: LocalImageViewmodel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_get_previous_images,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            dogImageAdapter = DogImageAdapter(requireContext())
            imageRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            imageRecycler.adapter = dogImageAdapter
            clearButton.setOnClickListener {
                viewModel.clearCache()
            }
        }
        collectUiState()
    }

    private fun collectUiState() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.distinctUntilChanged().collectLatest {
                    binding.apply {
                        noDataText.isVisible = it.noDataText
                        contentProgressBar.isVisible = it.isLoading
                        dogImageAdapter.setData(it.imgList.toMutableList())
                    }
                }
            }
        }
    }
}