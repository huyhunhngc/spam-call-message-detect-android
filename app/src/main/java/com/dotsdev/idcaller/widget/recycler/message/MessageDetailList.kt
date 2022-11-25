package com.dotsdev.idcaller.widget.recycler.message

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dotsdev.idcaller.adapter.BaseListAdapter
import com.dotsdev.idcaller.data.model.Message
import com.dotsdev.idcaller.databinding.ItemContactOrMessageBinding
import com.dotsdev.idcaller.widget.recycler.ContactMessageInfo
import com.xwray.groupie.Group
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Section

class MessageDetailList @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private val groupAdapter = GroupAdapter<GroupieViewHolder>()
    private var isOptionVisible = false
    private var isArrowVisible = true

    private var onItemClicked: ((key: Message, position: Int) -> Unit)? =
        null
    private lateinit var info: Message
    private lateinit var infos: List<Message>

    init {
        initializeViews()
    }

    private fun initializeViews() {
        this.adapter = groupAdapter
        this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
    }

    fun setMessageList(
        messages: List<Message>?
    ) {
        messages ?: return
        infos = messages
        groupAdapter.clear()
        messages.mapIndexed { index, info ->
            groupAdapter.add(
                Section().apply {
                    setFooter(buildItem(index, info))
                }
            )
        }
    }

    private fun buildItem(index: Int, info: Message): Group {
            return MessageDetailItem(
                info = info
            ).apply {
                setOnItemClicked { info, position ->
                    onItemClicked?.invoke(info, position)
                }
            }

    }

    fun setOnItemClicked(onItemClicked: ((info: Message, position: Int) -> Unit)?) {
        this.onItemClicked = onItemClicked
    }
}

class MessageDetailListAdapter :
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