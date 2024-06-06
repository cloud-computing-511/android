package com.example.cloudcompute.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.cloudcompute.R
import com.example.cloudcompute.base.BaseFragment
import com.example.cloudcompute.databinding.FragmentHomeBinding

class HomeFragment: BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
    }
}