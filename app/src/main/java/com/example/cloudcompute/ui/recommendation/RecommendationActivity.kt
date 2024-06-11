package com.example.cloudcompute.ui.recommendation

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cloudcompute.base.BaseActivity
import com.example.cloudcompute.databinding.ActivityRecommendBinding

class RecommendationActivity : BaseActivity<ActivityRecommendBinding>(ActivityRecommendBinding::inflate) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: RecommendationAdapter
    private lateinit var itemList: List<RecommendationItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recyclerView = binding.rvRecommendContent
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Sample data
        itemList = listOf(
            RecommendationItem(
                busStopName = "셔틀 승강장",
                subwayName = "주안역",
                busName = "셔틀버스",
                remainTime = "3분 전",
                estimatedTime = "예상 소요 20분"
            ),
            RecommendationItem(
                busStopName = "용현고갸교",
                subwayName = "주안역",
                busName = "511",
                remainTime = "2분 전",
                estimatedTime = "예상 소요 24분"
            )
            // 필요한 만큼 아이템 추가
        )

        recyclerViewAdapter = RecommendationAdapter(itemList)
        recyclerView.adapter = recyclerViewAdapter

        // 뒤로가기 버튼 클릭 이벤트
        binding.btnBack.setOnClickListener {
            finish() // 액티비티 종료
        }
    }
}