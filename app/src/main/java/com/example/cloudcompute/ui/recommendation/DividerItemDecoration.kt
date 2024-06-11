package com.example.cloudcompute.ui.recommendation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.DashPathEffect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.cloudcompute.R

class DividerItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val paint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.divider_color)
        style = Paint.Style.STROKE
        strokeWidth = context.resources.getDimension(R.dimen.divider_height)
        pathEffect = DashPathEffect(floatArrayOf(10f, 10f), 0f)
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + paint.strokeWidth.toInt()
            val path = Path().apply {
                moveTo(left.toFloat(), top.toFloat())
                lineTo(right.toFloat(), top.toFloat())
            }
            c.drawPath(path, paint)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.bottom = paint.strokeWidth.toInt()
    }
}