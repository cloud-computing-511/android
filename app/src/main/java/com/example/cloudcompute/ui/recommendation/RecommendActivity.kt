package com.example.cloudcompute.ui.recommendation

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cloudcompute.base.BaseActivity
import com.example.cloudcompute.databinding.ActivityRecommendBinding

class RecommendActivity : BaseActivity<ActivityRecommendBinding>(ActivityRecommendBinding::inflate) {

    private val viewModel: RecommendViewModel by viewModels()
    private val recyclerViewAdapter = RecommendAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.rvRecommendContent.apply {
            layoutManager = LinearLayoutManager(this@RecommendActivity)
            adapter = recyclerViewAdapter
            addItemDecoration(DividerItemDecoration(context)) // ItemDecoration 추가
        }

        // LiveData 옵저빙
        viewModel.busData.observe(this, Observer { data ->
            showLoading(true)
            recyclerViewAdapter.updateData(data)
            showLoading(false)
        })

        // 뒤로가기 버튼 클릭 이벤트
        binding.btnBack.setOnClickListener {
            finish() // 액티비티 종료
        }

        binding.btnRefresh.setOnClickListener {
            viewModel.fetchRecommend() // 새로고침 시 API 다시 호출
        }
    }

    private fun showLoading(show: Boolean) {
        if(show) {
            binding.progressBar.visibility = View.VISIBLE
        }
        if(!show) {
            binding.progressBar.visibility = View.GONE
        }
    }
}