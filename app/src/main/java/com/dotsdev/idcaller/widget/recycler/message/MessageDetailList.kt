package com.dotsdev.idcaller.widget.recycler.message

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dotsdev.idcaller.data.model.Message
import com.xwray.groupie.Group
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Section
import com.xwray.groupie.viewbinding.BindableItem

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
        return if (info.sentByMe) {
            MessageOwnerItem(
                info = info
            ).apply {
                setOnItemClicked { info, position ->
                    onItemClicked?.invoke(info, position)
                }
            }
        } else {
            MessagePeerItem(
                info = info
            ).apply {
                setOnItemClicked { info, position ->
                    onItemClicked?.invoke(info, position)
                }
            }
        }
    }

    fun setOnItemClicked(onItemClicked: ((info: Message, position: Int) -> Unit)?) {
        this.onItemClicked = onItemClicked
    }
}