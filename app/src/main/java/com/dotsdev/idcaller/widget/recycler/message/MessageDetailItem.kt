package com.dotsdev.idcaller.widget.recycler.message

import android.view.Gravity
import android.view.View
import androidx.core.content.ContextCompat
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.data.model.Message
import com.dotsdev.idcaller.databinding.ItemMessageOwnerBinding
import com.dotsdev.idcaller.utils.convertTimestampToHours
import com.xwray.groupie.viewbinding.BindableItem

class MessageDetailItem(private val info: Message) :
    BindableItem<ItemMessageOwnerBinding>() {
    override fun bind(viewBinding: ItemMessageOwnerBinding, position: Int) {
        viewBinding.apply {
            if (info.sentByMe) {
                rootLayout.gravity = Gravity.END
                content.background = ContextCompat.getDrawable(root.context, R.drawable.message_owner_background)
            } else {
                rootLayout.gravity = Gravity.START
                val background = if (info.isSpam) R.drawable.message_block_bg else R.drawable.message_peer_background
                content.background = ContextCompat.getDrawable(root.context, background)
            }
            content.text = info.content
            datetime.text = info.iat.time.convertTimestampToHours()
        }
    }

    override fun getLayout(): Int = R.layout.item_message_owner

    override fun initializeViewBinding(view: View): ItemMessageOwnerBinding =
        ItemMessageOwnerBinding.bind(view)
}
