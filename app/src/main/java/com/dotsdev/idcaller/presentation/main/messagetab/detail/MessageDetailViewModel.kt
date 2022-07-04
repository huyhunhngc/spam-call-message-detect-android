package com.dotsdev.idcaller.presentation.main.messagetab.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import com.dotsdev.idcaller.core.base.BaseViewModel
import com.dotsdev.idcaller.data.memory.message.MessageMemory
import com.dotsdev.idcaller.data.memory.message.SpamMessageMemory
import com.dotsdev.idcaller.data.model.Message
import com.dotsdev.idcaller.data.model.MessageGroup
import com.dotsdev.idcaller.utils.isPhoneNumber
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map

class MessageDetailViewModel(
    private val messageMemory: MessageMemory,
    private val spamMessageMemory: SpamMessageMemory,
    messageGroup: MessageGroup
) : BaseViewModel() {
    private val memoryMessageList = messageGroup.messages
    val contact = memoryMessageList.lastOrNull()?.from
    val isSupportReply = memoryMessageList.first().from.phoneNumber.isPhoneNumber()

    val message = MutableLiveData<List<Message>>()
    val messageTitle = MutableLiveData("")

    fun init() {
        message.postValue(memoryMessageList)
        messageTitle.postValue(
            memoryMessageList.first().let {
                it.from.callerName.ifEmpty {
                    it.contact.callerName
                }
            }
        )
        viewModelScope.launch {
            contact?.let {
                val spamMessage = spamMessageMemory.get()
                messageMemory.obs.asFlow().map {
                    (spamMessage + it).distinctBy { it.messageId }.filter { peer ->
                        peer.from.phoneNumber == contact.phoneNumber
                    }
                }.collectLatest {
                    message.postValue(it.sortedByDescending { it.iat })
                }
            }
        }
    }
}
