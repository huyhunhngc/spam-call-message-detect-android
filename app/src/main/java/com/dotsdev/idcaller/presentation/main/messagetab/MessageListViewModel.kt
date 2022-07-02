package com.dotsdev.idcaller.presentation.main.messagetab

import com.dotsdev.idcaller.core.SingleLiveEvent
import com.dotsdev.idcaller.core.base.BaseViewModel
import com.dotsdev.idcaller.data.model.Message
import com.dotsdev.idcaller.data.model.toMessageGroup
import com.dotsdev.idcaller.domain.message.query.GetMessageLog
import com.dotsdev.idcaller.widget.recycler.ContactMessageInfo
import com.dotsdev.idcaller.widget.recycler.toInfoData
import kotlinx.coroutines.flow.collectLatest

open class MessageListViewModel(
    private val getMessageLog: GetMessageLog,
) : BaseViewModel() {
    val listMessage = SingleLiveEvent<List<ContactMessageInfo>>()
    val detailClick = SingleLiveEvent<ContactMessageInfo>()

    init {
        viewModelScope.launch {
            getMessageLog.observeMessage().collectLatest { messages ->
                listMessage.postValue(
                    messages.toMessageGroup().map {
                        it.toInfoData()
                    }
                )
            }
        }
    }

    val onItemClick: ((info: ContactMessageInfo, position: Int) -> Unit) = { info, _ ->

    }
    val onToDetailClick: ((info: ContactMessageInfo, position: Int) -> Unit) = { info, _ ->
        detailClick.postValue(info)
    }
}