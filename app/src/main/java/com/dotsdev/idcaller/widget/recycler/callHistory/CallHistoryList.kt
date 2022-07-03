package com.dotsdev.idcaller.widget.recycler.callHistory

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dotsdev.idcaller.data.model.Call
import com.dotsdev.idcaller.data.model.Message
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Section

class CallHistoryList @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private val groupAdapter = GroupAdapter<GroupieViewHolder>()

    private var onItemClicked: ((key: Message, position: Int) -> Unit)? =
        null
    private lateinit var infos: List<Call>

    init {
        initializeViews()
    }

    private fun initializeViews() {
        this.adapter = groupAdapter
        this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
    }

    fun setCallList(
        calls: List<Call>?
    ) {
        calls ?: return
        groupAdapter.clear()
        groupAdapter.addAll(
            calls.mapIndexed { index, info ->
                CallHistoryItem(info)
            }
        )
    }

    fun setOnItemClicked(onItemClicked: ((info: Message, position: Int) -> Unit)?) {
        this.onItemClicked = onItemClicked
    }
}