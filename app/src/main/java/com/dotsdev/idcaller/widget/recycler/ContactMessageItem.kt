package com.dotsdev.idcaller.widget.recycler

import android.view.View
import androidx.core.view.isVisible
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.databinding.ItemContactOrMessageBinding
import com.dotsdev.idcaller.utils.getBackgroundColor
import com.dotsdev.idcaller.utils.getColorFromName
import com.dotsdev.idcaller.utils.getPrimaryColor
import com.xwray.groupie.viewbinding.BindableItem
import java.util.*

class ContactMessageItem(private val info: ContactMessageInfo) :
    BindableItem<ItemContactOrMessageBinding>() {
    override fun bind(viewBinding: ItemContactOrMessageBinding, position: Int) {
        viewBinding.apply {
            info = this@ContactMessageItem.info
            val hash = this@ContactMessageItem.info.peerName.getColorFromName()
            root.apply {
                if (!this@ContactMessageItem.info.unknownNumber) {
                    organizationImage.setBackgroundColor(
                        hash.getBackgroundColor()
                    )
                    avatarText.apply {
                        text = this@ContactMessageItem.info.peerName.substring(0, 1)
                            .uppercase(Locale.getDefault())
                        setTextColor(hash.getPrimaryColor())
                        isVisible = this@ContactMessageItem.info.peerPhotoUrl.isBlank()
                    }
                }
                this@ContactMessageItem.info.subLineStartIcon?.let {
                    organizationSubText.setCompoundDrawablesWithIntrinsicBounds(
                        it, 0, 0, 0
                    )
                }
            }
        }
    }

    override fun getLayout(): Int = R.layout.item_contact_or_message

    override fun initializeViewBinding(view: View): ItemContactOrMessageBinding =
        ItemContactOrMessageBinding.bind(view)
}
