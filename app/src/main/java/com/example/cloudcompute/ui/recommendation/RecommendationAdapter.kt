package com.example.cloudcompute.ui.recommendation

import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cloudcompute.R

class RecommendationAdapter(private val itemList: List<RecommendationItem>) : RecyclerView.Adapter<RecommendationAdapter.RecommendationViewHolder>() {

    class RecommendationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val busStopNameTextView: TextView = itemView.findViewById(R.id.tv_bus_stop_name)
        val subwayNameTextView: TextView = itemView.findViewById(R.id.tv_subway_name)
        val busNameTextView: TextView = itemView.findViewById(R.id.tv_bus_name)
        val remainTimeTextView: TextView = itemView.findViewById(R.id.tv_remain_time)
        val estimatedTimeTextView: TextView = itemView.findViewById(R.id.tv_estimated_time)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecommendationViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recommendation, parent, false)
        return RecommendationViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: RecommendationAdapter.RecommendationViewHolder,
        position: Int
    ) {
        val currentItem = itemList[position]
        holder.busStopNameTextView.text = currentItem.busStopName
        holder.subwayNameTextView.text = currentItem.subwayName
        holder.busNameTextView.text = currentItem.busName
        holder.remainTimeTextView.text = currentItem.remainTime

        // 예상 소요 시간 텍스트 설정
        val estimatedTimeText = currentItem.estimatedTime
        val spannableString = SpannableString(estimatedTimeText)
        val start = estimatedTimeText.indexOfFirst { it.isDigit() }
        val end = estimatedTimeText.indexOfFirst { it == '분' }

        if (start >= 0 && end > start) {
            spannableString.setSpan(StyleSpan(Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannableString.setSpan(RelativeSizeSpan(1.2f), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        holder.estimatedTimeTextView.text = spannableString
    }

    override fun getItemCount() = itemList.size
}