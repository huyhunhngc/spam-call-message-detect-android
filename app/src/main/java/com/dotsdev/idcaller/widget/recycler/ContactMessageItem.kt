package com.dotsdev.idcaller.widget.recycler

import android.view.View
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.databinding.ItemContactOrMessageBinding
import com.xwray.groupie.viewbinding.BindableItem

class ContactMessageItem(private val info: ContactMessageInfo) :
    BindableItem<ItemContactOrMessageBinding>() {
    override fun bind(viewBinding: ItemContactOrMessageBinding, position: Int) {
        viewBinding.apply {
            info = this@ContactMessageItem.info
        }
    }

    override fun getLayout(): Int = R.layout.item_contact_or_message

    override fun initializeViewBinding(view: View): ItemContactOrMessageBinding =
        ItemContactOrMessageBinding.bind(view)
}