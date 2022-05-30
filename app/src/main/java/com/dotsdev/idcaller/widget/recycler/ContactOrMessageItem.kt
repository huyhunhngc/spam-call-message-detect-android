package com.dotsdev.idcaller.widget.recycler

import android.view.View
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.databinding.ItemContactOrMessageBinding
import com.xwray.groupie.viewbinding.BindableItem

class ContactOrMessageItem(private val info: ContactOrMessageInfo) :
    BindableItem<ItemContactOrMessageBinding>() {
    override fun bind(viewBinding: ItemContactOrMessageBinding, position: Int) {
        viewBinding.apply {
            info = this@ContactOrMessageItem.info
        }
    }

    override fun getLayout(): Int = R.layout.item_contact_or_message

    override fun initializeViewBinding(view: View): ItemContactOrMessageBinding =
        ItemContactOrMessageBinding.bind(view)
}