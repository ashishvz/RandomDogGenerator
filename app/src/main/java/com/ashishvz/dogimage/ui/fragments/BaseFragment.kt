package com.ashishvz.dogimage.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ashishvz.dogimage.R
import com.ashishvz.dogimage.databinding.BaseLayoutBinding

class BaseFragment : Fragment() {

    private lateinit var binding: BaseLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.base__layout, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            generateImageFragmentButton.setOnClickListener {
                findNavController().navigate(R.id.action_baseFragment_to_generateImageFragment)
            }
            showImageFragmentButton.setOnClickListener {
                findNavController().navigate(R.id.action_baseFragment_to_getPreviousImageFragment)
            }
        }
    }
}