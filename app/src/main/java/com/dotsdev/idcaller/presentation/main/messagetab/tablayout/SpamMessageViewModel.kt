package com.dotsdev.idcaller.presentation.main.messagetab.tablayout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.dotsdev.idcaller.core.SingleLiveEvent
import com.dotsdev.idcaller.core.base.BaseViewModel
import com.dotsdev.idcaller.data.dao.RoomRepository
import com.dotsdev.idcaller.data.model.Message
import com.dotsdev.idcaller.data.model.toMessageGroup
import com.dotsdev.idcaller.domain.detectSpam.DetectSpamMessage
import com.dotsdev.idcaller.domain.message.query.GetMessageLog
import com.dotsdev.idcaller.widget.recycler.ContactMessageInfo
import com.dotsdev.idcaller.widget.recycler.toInfoData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext

class SpamMessageViewModel(
    private val detectSpamMessage: DetectSpamMessage,
    private val roomRepository: RoomRepository,
    getMessageLog: GetMessageLog,
) : BaseViewModel() {
    val listMessage = MutableLiveData<List<ContactMessageInfo>>(listOf())
    val detailClick = SingleLiveEvent<ContactMessageInfo>()

    val isListEmpty = listMessage.map {
        it.isEmpty()
    }

    init {
        CoroutineScope(EmptyCoroutineContext).launch(Dispatchers.IO) {
            getMessageLog.observeMessage().collect { messages ->
                messages.map {
                    it.copy(isSpam = detectSpamMessage(it.content) && it.sentByMe.not())
                }.let {
                    it.forEach {
                        kotlin.runCatching {
                            roomRepository.spamMessageDao().insert(it)
                        }
                    }
                    listMessage.postValue(it.convertToInfoData())
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModelScope.launch {
            roomRepository.spamMessageDao().getMessages().let {
                listMessage.postValue(it?.mapNotNull { it }?.convertToInfoData())
            }
        }
    }

    private fun List<Message>.convertToInfoData(): List<ContactMessageInfo> {
        return this.toMessageGroup().filter { it.messages.any { it.isSpam } }
            .map { it.toInfoData() }
    }

    val onItemClick: ((info: ContactMessageInfo, position: Int) -> Unit) = { info, _ ->

    }
    val onToDetailClick: ((info: ContactMessageInfo, position: Int) -> Unit) = { info, _ ->
        detailClick.postValue(info)
    }
}
