package com.dotsdev.idcaller.presentation.main.messagetab.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.dotsdev.idcaller.core.base.BaseViewModel
import com.dotsdev.idcaller.data.memory.message.MessageMemory
import com.dotsdev.idcaller.data.model.Message
import com.dotsdev.idcaller.data.model.MessageGroup
import com.dotsdev.idcaller.utils.isPhoneNumber
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
    val isSupportReply = memoryMessageList.first().contact.phoneNumber.isPhoneNumber()

    val messageTitle = MutableLiveData("")

    fun init() {
        Log.d("!@#", "init: ${memoryMessageList.first().contact.phoneNumber}")
        messageTitle.postValue(memoryMessageList.first().contact.callerName)
        viewModelScope.launch {
            contact?.let {
                messageMemory.observe(it).collectLatest {
                    message.postValue(it)
                }
            }
        }
    }
}
