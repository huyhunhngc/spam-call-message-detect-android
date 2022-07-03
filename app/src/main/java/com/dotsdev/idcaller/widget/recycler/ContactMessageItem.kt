package com.dotsdev.idcaller.widget.recycler

import android.annotation.SuppressLint
import android.view.View
import androidx.core.view.isVisible
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.databinding.ItemContactOrMessageBinding
import com.dotsdev.idcaller.utils.*
import com.xwray.groupie.viewbinding.BindableItem
import java.util.*

class ContactMessageItem(private val info: ContactMessageInfo) :
    BindableItem<ItemContactOrMessageBinding>() {
    private var onItemOptionClicked: ((info: ContactMessageInfo, position: Int) -> Unit)? =
        null

    private var onItemClicked: ((info: ContactMessageInfo, position: Int) -> Unit)? =
        null

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun bind(viewBinding: ItemContactOrMessageBinding, position: Int) {
        viewBinding.apply {
            info = this@ContactMessageItem.info
            val isSpam =
                (this@ContactMessageItem.info.dataFrom as? FromData.FromMessageGroup)?.
                messageGroup?.messages?.any { it.isSpam } ?: false
            val hash = if (isSpam) {
                Colors.RED
            } else {
                this@ContactMessageItem.info.peerName.getColorFromName()
            }
            root.apply {
                setOnClickListener {
                    onItemClicked?.invoke(this@ContactMessageItem.info, position)
                }
                organizationImage.setBackgroundColor(
                    hash.getBackgroundColor()
                )
                if (!this@ContactMessageItem.info.unknownNumber) {
                    avatarText.apply {
                        text = kotlin.runCatching {
                            this@ContactMessageItem.info.peerName.substring(0, 1)
                                .uppercase(Locale.getDefault())
                        }.getOrDefault("")
                        setTextColor(hash.getPrimaryColor())
                        isVisible = this@ContactMessageItem.info.peerPhotoUrl.isBlank()
                    }
                    avatarIcon.isVisible = false
                } else {
                    avatarText.isVisible = false
                    avatarIcon.isVisible = true
                    avatarIcon.setColorFilter(hash.getPrimaryColor())
                    val isOrganization = this@ContactMessageItem.info.peerName.isPhoneNumber().not()
                    if (isOrganization) {
                        avatarIcon.setImageDrawable(
                            resources.getDrawable(
                                R.drawable.ic_baseline_business,
                                null
                            )
                        )
                    } else {
                        avatarIcon.setImageDrawable(
                            resources.getDrawable(
                                R.drawable.ic_baseline_perm_identity,
                                null
                            )
                        )
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

    fun setOnItemOptionClicked(onItemOptionClicked: ((info: ContactMessageInfo, position: Int) -> Unit)) {
        this.onItemOptionClicked = onItemOptionClicked
    }

    fun setOnItemClicked(onItemClicked: ((info: ContactMessageInfo, position: Int) -> Unit)) {
        this.onItemClicked = onItemClicked
    }
}
