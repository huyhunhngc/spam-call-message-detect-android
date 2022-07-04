package com.dotsdev.idcaller.presentation.main.messagetab.tablayout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.dotsdev.idcaller.core.SingleLiveEvent
import com.dotsdev.idcaller.core.base.BaseViewModel
import com.dotsdev.idcaller.data.model.Message
import com.dotsdev.idcaller.data.model.toMessageGroup
import com.dotsdev.idcaller.domain.message.query.GetMessageLog
import com.dotsdev.idcaller.widget.recycler.ContactMessageInfo
import com.dotsdev.idcaller.widget.recycler.toInfoData
import kotlinx.coroutines.flow.collectLatest

class SpamMessageViewModel(
    private val getMessageLog: GetMessageLog,
) : BaseViewModel() {
    val listMessage = MutableLiveData<List<ContactMessageInfo>>(listOf())
    val detailClick = SingleLiveEvent<ContactMessageInfo>()

    val isListEmpty = listMessage.map {
        it.isEmpty()
    }

    override fun onStart() {
        super.onStart()
        viewModelScope.launch {
            getMessageLog.observeSpam().collectLatest {
                listMessage.postValue(it.convertToInfoData())
            }
        }
    }

    private fun List<Message>.convertToInfoData(): List<ContactMessageInfo> {
        return this.toMessageGroup().filter { it.messages.any { it.isSpam } }
            .map { it.toInfoData() }
    }

    val onToDetailClick: ((info: ContactMessageInfo, position: Int) -> Unit) = { info, _ ->
        detailClick.postValue(info)
    }
}
