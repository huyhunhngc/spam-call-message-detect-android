package com.dotsdev.idcaller.widget.recycler.callHistory

import android.annotation.SuppressLint
import android.view.View
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.data.model.Call
import com.dotsdev.idcaller.databinding.ItemCallHistoryBinding
import com.dotsdev.idcaller.widget.recycler.ContactMessageInfo
import com.xwray.groupie.viewbinding.BindableItem

class CallHistoryItem(private val info: Call) :
    BindableItem<ItemCallHistoryBinding>() {
    private var onItemClicked: ((info: ContactMessageInfo, position: Int) -> Unit)? =
        null

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun bind(viewBinding: ItemCallHistoryBinding, position: Int) {
        viewBinding.apply {
            info = this@CallHistoryItem.info

            root.apply {
                this@CallHistoryItem.info.callType.icon.let {
                    organizationSubText.setCompoundDrawablesWithIntrinsicBounds(
                        it, 0, 0, 0
                    )
                }
            }
        }
    }

    override fun getLayout(): Int = R.layout.item_call_history

    override fun initializeViewBinding(view: View): ItemCallHistoryBinding =
        ItemCallHistoryBinding.bind(view)


    fun setOnItemClicked(onItemClicked: ((info: ContactMessageInfo, position: Int) -> Unit)) {
        this.onItemClicked = onItemClicked
    }
}
