package com.dotsdev.idcaller.presentation.main.messagetab

import androidx.lifecycle.MutableLiveData
import com.dotsdev.idcaller.core.SingleLiveEvent
import com.dotsdev.idcaller.core.base.BaseViewModel
import com.dotsdev.idcaller.data.model.toMessageGroup
import com.dotsdev.idcaller.domain.message.query.GetMessageLog
import com.dotsdev.idcaller.widget.recycler.ContactMessageInfo
import com.dotsdev.idcaller.widget.recycler.toInfoData
import kotlinx.coroutines.flow.collectLatest

open class MessageListViewModel(
    private val getMessageLog: GetMessageLog
) : BaseViewModel() {
    val listMessage = MutableLiveData<List<ContactMessageInfo>>()
    val messageClick = SingleLiveEvent<ContactMessageInfo>()
    val detailClick = SingleLiveEvent<ContactMessageInfo>()
    override fun onStart() {
        super.onStart()
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
        messageClick.postValue(info)
    }
    val onToDetailClick: ((info: ContactMessageInfo, position: Int) -> Unit) = { info, _ ->
        detailClick.postValue(info)
    }
}