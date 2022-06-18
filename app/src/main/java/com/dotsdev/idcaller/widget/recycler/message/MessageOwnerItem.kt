package com.dotsdev.idcaller.widget.recycler.message

import android.view.View
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.data.model.Message
import com.dotsdev.idcaller.databinding.ItemMessageOwnerBinding
import com.dotsdev.idcaller.utils.convertTimestampToHours
import com.xwray.groupie.viewbinding.BindableItem

class MessageOwnerItem(private val info: Message) :
    BindableItem<ItemMessageOwnerBinding>() {
    override fun bind(viewBinding: ItemMessageOwnerBinding, position: Int) {
        viewBinding.apply {
            contentMessageOwner.text = this@MessageOwnerItem.info.content
            datetimeMessageOwner.text =
                this@MessageOwnerItem.info.iat.time.convertTimestampToHours()
        }
    }

    override fun getLayout(): Int = R.layout.item_message_owner

    override fun initializeViewBinding(view: View): ItemMessageOwnerBinding =
        ItemMessageOwnerBinding.bind(view)
}
