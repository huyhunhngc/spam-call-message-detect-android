package com.dotsdev.idcaller.utils

import android.os.Build
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.dotsdev.idcaller.R
import com.google.android.material.bottomnavigation.BottomNavigationView

@RequiresApi(Build.VERSION_CODES.M)
private fun BottomNavigationView.setBadgeCountOfNotificationButton(
    @IdRes navigationId: Int,
    value: Int
) {
    getOrCreateBadge(navigationId).run {
        backgroundColor = resources.getColor(R.color.colorPrimary, null)
        badgeTextColor = resources.getColor(R.color.colorTextOnPrimary, null)
        horizontalOffset = 10
        verticalOffset = 10

        if (value == 0) {
            clearNumber()
            isVisible = false
        } else {
            number = value
            isVisible = true
        }
    }
}

@BindingAdapter("visible_or_gone")
fun View.setVisibleOrGone(isVisible: Boolean?) {
    visibility = if (isVisible == true) View.VISIBLE else View.GONE
}
