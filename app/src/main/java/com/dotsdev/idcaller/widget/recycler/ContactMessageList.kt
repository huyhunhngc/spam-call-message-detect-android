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
    private lateinit var infos: List<ContactMessageInfo>

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

    fun setInfoList(
        contacts: List<ContactMessageInfo>?
    ) {
        contacts ?: return
        infos = contacts
        groupAdapter.clear()
        groupAdapter.add(Section().apply {
            update(contacts.map { info ->
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
            })
        })
    }

    fun setOnItemArrowClicked(onItemOptionClicked: ((info: ContactMessageInfo, position: Int) -> Unit)?) {
        this.onItemOptionClicked = onItemOptionClicked
    }

    fun setOnItemClicked(onItemClicked: ((info: ContactMessageInfo, position: Int) -> Unit)?) {
        this.onItemClicked = onItemClicked
    }
}