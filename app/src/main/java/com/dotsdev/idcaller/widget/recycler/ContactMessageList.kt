package com.dotsdev.idcaller.widget.recycler

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dotsdev.idcaller.adapter.BaseListAdapter
import com.dotsdev.idcaller.databinding.ItemContactOrMessageBinding

class ContactMessageList @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private val groupAdapter = ContactMessageListAdapter()
    private var isOptionVisible = false
    private var isArrowVisible = true
    private var recentInfo: List<ContactMessageInfo> = listOf()

    init {
        initializeViews()
    }

    private fun initializeViews() {
        this.adapter = groupAdapter
        this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    fun setOptionVisible(isOptionVisible: Boolean = false) {
        this.isOptionVisible = isOptionVisible
    }

    fun setArrowVisible(isArrowVisible: Boolean = true) {
        this.isArrowVisible = isArrowVisible
    }

    fun setRecentList(
        contacts: List<ContactMessageInfo>?
    ) {
        val data = contacts ?: return
        recentInfo = data
    }

    fun setInfoList(
        contacts: List<ContactMessageInfo>?
    ) {
        val contactInfo = contacts?.mapIndexed { index, info ->
            val needSection = runCatching {
                contacts[index - 1].peerName[0] != info.peerName[0] && info.type == ItemType.CONTACT
            }.getOrDefault(false)
            info.copy(showAlphabetSection = needSection)
        }.orEmpty()
        groupAdapter.submitList(contactInfo)
    }

    fun setOnItemArrowClicked(onItemOptionClicked: ((info: ContactMessageInfo, position: Int) -> Unit)?) {
        //this.onItemOptionClicked = onItemOptionClicked
    }

    fun setOnItemClicked(onItemClicked: ((info: ContactMessageInfo, position: Int) -> Unit)?) {
        onItemClicked?.let { groupAdapter.setOnItemClicked(it) }
    }
}

class ContactMessageListAdapter :
    BaseListAdapter<ContactMessageInfo, ItemContactOrMessageBinding>() {
    private var onItemOptionClicked: ((info: ContactMessageInfo, position: Int) -> Unit)? = null

    private var onItemClicked: ((info: ContactMessageInfo, position: Int) -> Unit)? = null

    override fun createBinding(parent: ViewGroup): ItemContactOrMessageBinding {
        return ItemContactOrMessageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    }

    override fun onBind(
        binding: ItemContactOrMessageBinding,
        data: ContactMessageInfo,
        position: Int
    ) {
        binding.apply {
            info = data
            organizationImage.setBackgroundColor(data.getColorBackground())
            headerText.isVisible = data.showAlphabetSection
            headerText.text = data.firstCharacterOfName

            data.unknownNumberIcon?.let {
                avatarText.isVisible = false
                avatarIcon.isVisible = true
                avatarIcon.setColorFilter(data.getPrimaryColor())
                avatarIcon.setImageDrawable(root.resources.getDrawable(it, null))
            } ?: kotlin.run {
                avatarText.text = data.firstCharacterOfName
                avatarText.setTextColor(data.getPrimaryColor())
                avatarText.isVisible = data.peerPhotoUrl.isBlank()
                avatarIcon.isVisible = false
            }
            data.subLineStartIcon?.let {
                organizationSubText.setCompoundDrawablesWithIntrinsicBounds(
                    it, 0, 0, 0
                )
            }
            root.setOnClickListener { onItemClicked?.invoke(data, position) }
        }
    }

    fun setOnItemOptionClicked(onItemOptionClicked: ((info: ContactMessageInfo, position: Int) -> Unit)) {
        this.onItemOptionClicked = onItemOptionClicked
    }

    fun setOnItemClicked(onItemClicked: ((info: ContactMessageInfo, position: Int) -> Unit)) {
        this.onItemClicked = onItemClicked
    }
}
