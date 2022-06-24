package com.dotsdev.idcaller.widget.recycler.message

import android.view.View
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.data.model.Message
import com.dotsdev.idcaller.databinding.ItemMessagePeerBinding
import com.dotsdev.idcaller.utils.convertTimestampToHours
import com.xwray.groupie.viewbinding.BindableItem

class MessagePeerItem(private val info: Message) :
    BindableItem<ItemMessagePeerBinding>() {
    override fun bind(viewBinding: ItemMessagePeerBinding, position: Int) {
        viewBinding.apply {
            info = this@MessagePeerItem.info
        }
    }

    override fun getLayout(): Int = R.layout.item_message_peer

    override fun initializeViewBinding(view: View): ItemMessagePeerBinding =
        ItemMessagePeerBinding.bind(view)
}
