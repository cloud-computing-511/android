package com.example.cloudcompute.ui.main.home

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.cloudcompute.R
import com.example.cloudcompute.base.BaseFragment
import com.example.cloudcompute.databinding.FragmentHomeBinding
import com.example.cloudcompute.ui.recommendation.RecommendActivity
import com.motax.modutaxi.presentation.customview.LoadingDialog
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var loadingDialog: LoadingDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = LoadingDialog(requireContext())

        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.ivRefresh.setOnClickListener {
            viewModel.fetchCongestion()
        }

        binding.btnFindAlternative.setOnClickListener {
            val intent = Intent(requireContext(), RecommendActivity::class.java)
            startActivity(intent)
        }

        binding.layoutHome.visibility = View.GONE

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    if (!state.isLoading) {
                        binding.layoutHome.visibility = View.VISIBLE
                    }
                }
            }
        }

        viewModel.fetchCongestion()

    }



}