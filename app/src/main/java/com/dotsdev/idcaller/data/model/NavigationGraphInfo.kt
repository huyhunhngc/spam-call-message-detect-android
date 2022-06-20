package com.dotsdev.idcaller.data.model

import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import com.dotsdev.idcaller.widget.recycler.FromData
import java.io.Serializable

data class NavigationGraphInfo(
    @NavigationRes
    val graphId: Int,
    @IdRes
    val startDestinationId: Int,
    val dataFrom: FromData? = null
) : Serializable