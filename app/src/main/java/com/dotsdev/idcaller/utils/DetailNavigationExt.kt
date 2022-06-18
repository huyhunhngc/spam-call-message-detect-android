package com.dotsdev.idcaller.utils

import androidx.navigation.NavDirections
import com.dotsdev.idcaller.presentation.main.mainflow.MainFlowFragment
import com.dotsdev.idcaller.presentation.main.mainflow.MainFlowFragmentDirections
import com.dotsdev.idcaller.widget.recycler.ContactMessageInfo
import com.dotsdev.idcaller.widget.recycler.FromData

fun ContactMessageInfo.toDetail(): NavDirections? {
    return when (val dataFrom = this.dataFrom) {
        is FromData.FromMessageGroup -> {
            MainFlowFragmentDirections.openDetailMessage(dataFrom.messageGroup)
        }
        else -> {
            null
        }
    }
}
