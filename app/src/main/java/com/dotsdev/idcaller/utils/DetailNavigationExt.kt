package com.dotsdev.idcaller.utils

import androidx.navigation.NavDirections
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.data.model.NavigationGraphInfo
import com.dotsdev.idcaller.presentation.main.messagetab.MessageListFragmentDirections
import com.dotsdev.idcaller.widget.recycler.ContactMessageInfo
import com.dotsdev.idcaller.widget.recycler.FromData

fun ContactMessageInfo.toDetail(): NavDirections? {
    return when (val dataFrom = this.dataFrom) {
        is FromData.FromMessageGroup -> {
            MessageListFragmentDirections.openDetailMessage(
                NavigationGraphInfo(
                    graphId = R.navigation.message_detail_navigation,
                    startDestinationId = R.id.detail_message,
                    dataFrom = dataFrom
                )
            )
        }
        else -> {
            null
        }
    }
}
