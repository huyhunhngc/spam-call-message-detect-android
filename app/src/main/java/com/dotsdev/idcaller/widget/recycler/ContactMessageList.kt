package com.dotsdev.idcaller.widget.recycler

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Section

class ContactMessageList @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private val groupAdapter = GroupAdapter<GroupieViewHolder>()
    private var isOptionVisible = false
    private var isArrowVisible = true
    private var onItemOptionClicked: ((key: ContactMessageInfo, position: Int) -> Unit)? =
        null

    private var onItemClicked: ((key: ContactMessageInfo, position: Int) -> Unit)? =
        null
    private lateinit var info: ContactMessageInfo
    private var infos: List<ContactMessageInfo> = listOf()
    private var recentInfos: List<ContactMessageInfo> = listOf()

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
        recentInfos = data
        groupAdapter.add(0,
            Section().apply {
                setFooter(
                    RecentContactListItem(
                        info = ListContactMessageInfo(data)
                    )
                )
            }
        )
    }

    fun setInfoList(
        contacts: List<ContactMessageInfo>?
    ) {
        contacts ?: return
        if (infos.isEmpty() || contacts.firstOrNull()?.dataFrom !is FromData.FromMessageGroup) {
            groupAdapter.clear()
            groupAdapter.addAll(
                contacts.mapIndexed { index, info ->
                    val needSection = kotlin.runCatching {
                        contacts[index - 1].peerName[0] != info.peerName[0]
                    }.getOrDefault(true)

                    Section().apply {
                        if (needSection && info.type == ItemType.CONTACT) {
                            setHeader(SectionAlphabetItem(info.peerName.substring(0, 1)))
                        }
                        setFooter(
                            ContactMessageItem(
                                info = info
                            ).apply {
                                setOnItemArrowClicked { info, position ->
                                    this@ContactMessageList.info = info

                                }
                                setOnItemClicked { info, position ->
                                    onItemClicked?.invoke(info, position)
                                }
                            }
                        )
                    }
                }
            )
        }
        if (contacts.firstOrNull()?.dataFrom is FromData.FromMessageGroup) {
            if (infos == contacts) return
            val needUpdate = mutableListOf<ContactMessageInfo>()
            contacts.forEach { item ->
                infos.find { it.peerName == item.peerName }?.let {
                    if (it.subLine != item.subLine) {
                        needUpdate.add(item)
                    }
                }
            }
            var removeCount = 0
            infos.forEachIndexed { index, info ->
                if (needUpdate.map { it.peerName }.contains(info.peerName)) {
                    kotlin.runCatching {
                        groupAdapter.removeGroupAtAdapterPosition(index - removeCount)
                        removeCount += 1
                    }
                }
            }
            needUpdate.forEach { info ->
                groupAdapter.add(0,
                    Section().apply {
                        setFooter(
                            ContactMessageItem(
                                info = info
                            ).apply {
                                setOnItemArrowClicked { info, position ->
                                    this@ContactMessageList.info = info
                                }
                                setOnItemClicked { info, position ->
                                    onItemClicked?.invoke(info, position)
                                }
                            }
                        )
                    }
                )
                this.smoothScrollToPosition(0)
            }
        }
        infos = contacts
    }

    fun setOnItemArrowClicked(onItemOptionClicked: ((info: ContactMessageInfo, position: Int) -> Unit)?) {
        this.onItemOptionClicked = onItemOptionClicked
    }

    fun setOnItemClicked(onItemClicked: ((info: ContactMessageInfo, position: Int) -> Unit)?) {
        this.onItemClicked = onItemClicked
    }
}
