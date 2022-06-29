package com.dotsdev.idcaller.widget.recycler

import android.view.View
import androidx.core.view.isVisible
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.databinding.ItemRecentContactBinding
import com.dotsdev.idcaller.utils.getBackgroundColor
import com.dotsdev.idcaller.utils.getColorFromName
import com.dotsdev.idcaller.utils.getPrimaryColor
import com.xwray.groupie.viewbinding.BindableItem
import java.util.Locale

class RecentContactItem(private val info: ContactMessageInfo) :
    BindableItem<ItemRecentContactBinding>() {
    override fun bind(viewBinding: ItemRecentContactBinding, position: Int) {
        viewBinding.apply {
            info = this@RecentContactItem.info
            root.apply {
                this@RecentContactItem.info.subLineStartIcon?.let {
                    organizationSubText.setCompoundDrawablesWithIntrinsicBounds(
                        it, 0, 0, 0
                    )
                }
            }
        }
    }

    override fun getLayout(): Int = R.layout.item_recent_contact

    override fun initializeViewBinding(view: View): ItemRecentContactBinding =
        ItemRecentContactBinding.bind(view)
}
