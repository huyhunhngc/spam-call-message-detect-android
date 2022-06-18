package com.dotsdev.idcaller.widget.recycler

import android.view.View
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.databinding.ItemHeaderLayoutBinding
import com.xwray.groupie.viewbinding.BindableItem

class SectionAlphabetItem(val alphabet: String) : BindableItem<ItemHeaderLayoutBinding>() {
    override fun bind(viewBinding: ItemHeaderLayoutBinding, position: Int) {
        viewBinding.apply {
            headerText.text = alphabet
        }
    }

    override fun getLayout(): Int = R.layout.item_header_layout

    override fun initializeViewBinding(view: View): ItemHeaderLayoutBinding =
        ItemHeaderLayoutBinding.bind(view)
}
