package com.fiveoneone.cloudcompute.bindingAdapters

import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.fiveoneone.cloudcompute.R
import com.fiveoneone.cloudcompute.ui.main.home.Status

@BindingAdapter("statusBackground")
fun setStatusBackground(view: View, status: Status?) {
    status?.let {
        val color = when (it) {
            Status.LEISURELY -> R.color.leisurely_main
            Status.NORMAL -> R.color.normal_main
            Status.CROWDED -> R.color.crowded_main
            Status.SENSOR_ERROR -> R.color.error_main
            Status.SENSOR_INACTIVE -> R.color.inactive_main
        }
        view.setBackgroundColor(view.context.getColor(color))
    }
}

@BindingAdapter("statusBackgroundResource")
fun setStatusBackgroundResource(button: Button, status: Status?) {
    status?.let {
        val backgroundResource = when (it) {
            Status.LEISURELY -> R.drawable.rect_leisure
            Status.NORMAL -> R.drawable.rect_common
            Status.CROWDED -> R.drawable.rect_crowded
            Status.SENSOR_ERROR -> R.drawable.rect_error
            Status.SENSOR_INACTIVE -> R.drawable.rect_inactive
        }
        button.setBackgroundResource(backgroundResource)
    }
}

@BindingAdapter("statusImage")
fun setStatusImage(imageView: ImageView, status: Status?) {
    status?.let {
        val imageResource = when (it) {
            Status.LEISURELY -> R.drawable.ic_leisure
            Status.NORMAL -> R.drawable.ic_normal
            Status.CROWDED -> R.drawable.ic_crowded
            Status.SENSOR_ERROR -> R.drawable.ic_error
            Status.SENSOR_INACTIVE -> R.drawable.ic_inactive
        }
        imageView.setImageResource(imageResource)
    }
}