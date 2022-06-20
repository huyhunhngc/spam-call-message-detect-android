package com.dotsdev.idcaller.presentation.main.messagetab.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.dotsdev.idcaller.core.base.BaseViewModel
import com.dotsdev.idcaller.data.memory.message.MessageMemory
import com.dotsdev.idcaller.data.model.Message
import com.dotsdev.idcaller.data.model.MessageGroup
import kotlinx.coroutines.flow.collectLatest

class MessageDetailViewModel(
    private val messageMemory: MessageMemory,
    messageGroup: MessageGroup
) : BaseViewModel() {
    val message = MutableLiveData<List<Message>>()
    val memoryMessageList = messageGroup.messages
    val contact = memoryMessageList.lastOrNull()?.from
    val isEmptyMessage = message.map {
        it.isEmpty()
    }

    val messageTitle = MutableLiveData("")

    fun init() {
        messageTitle.postValue(contact?.callerName?.ifEmpty {
            contact.phoneNumber
        })
        viewModelScope.launch {
            contact?.let {
                messageMemory.observe(it).collectLatest {
                    message.postValue(it)
                }
            }
        }
    }
}
