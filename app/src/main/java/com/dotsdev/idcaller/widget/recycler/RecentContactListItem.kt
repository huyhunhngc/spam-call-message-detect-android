package com.dotsdev.idcaller.widget.recycler

import android.view.View
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.databinding.ItemRecentContactListBinding
import com.xwray.groupie.viewbinding.BindableItem

class RecentContactListItem(private val info: ListContactMessageInfo) :
    BindableItem<ItemRecentContactListBinding>() {
    override fun bind(viewBinding: ItemRecentContactListBinding, position: Int) {
        viewBinding.apply {
            data = info
        }
    }

    override fun getLayout(): Int = R.layout.item_recent_contact_list

    override fun initializeViewBinding(view: View): ItemRecentContactListBinding =
        ItemRecentContactListBinding.bind(view)
}