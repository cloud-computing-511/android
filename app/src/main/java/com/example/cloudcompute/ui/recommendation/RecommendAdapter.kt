package com.example.cloudcompute.ui.recommendation

import android.graphics.Typeface
import android.os.CountDownTimer
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
import java.util.concurrent.TimeUnit

class RecommendAdapter(private var itemList: MutableList<RecommendItem>) : RecyclerView.Adapter<RecommendAdapter.RecommendViewHolder>() {

    class RecommendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val busStopNameTextView: TextView = itemView.findViewById(R.id.tv_bus_stop_name)
        val subwayNameTextView: TextView = itemView.findViewById(R.id.tv_subway_name)
        val busNameTextView: TextView = itemView.findViewById(R.id.tv_bus_name)
        val remainTimeTextView: TextView = itemView.findViewById(R.id.tv_remain_time)
        val estimatedTimeTextView: TextView = itemView.findViewById(R.id.tv_estimated_time)
        var countDownTimer: CountDownTimer? = null // 타이머를 보관하기 위한 변수
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recommend, parent, false)
        return RecommendViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecommendViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.busStopNameTextView.text = currentItem.busStopName
        holder.subwayNameTextView.text = currentItem.subwayName
        holder.busNameTextView.text = currentItem.busName

        // 이전 타이머가 있다면 취소
        holder.countDownTimer?.cancel()

        // 남은 시간 텍스트 설정
        holder.countDownTimer = object : CountDownTimer(currentItem.remainTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                currentItem.remainTime = millisUntilFinished
                val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60
                holder.remainTimeTextView.text = String.format("%d분 %d초 전", minutes, seconds)
            }

            override fun onFinish() {
                holder.remainTimeTextView.text = "가능한 버스가 없음"
            }
        }.start()

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

    fun updateData(newItemList: List<RecommendItem>) {
        itemList.clear()
        itemList.addAll(newItemList)
        notifyDataSetChanged()
    }
}