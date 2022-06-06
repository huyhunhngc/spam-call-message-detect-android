package com.dotsdev.idcaller.presentation.main.messagetab

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.dotsdev.idcaller.core.base.BaseViewModel
import com.dotsdev.idcaller.data.memory.message.MessageMemory
import com.dotsdev.idcaller.data.model.Contact
import com.dotsdev.idcaller.data.model.Message
import kotlinx.coroutines.flow.collectLatest

class MessageDetailViewModel(private val messageMemory: MessageMemory): BaseViewModel() {
    val message = MutableLiveData<List<Message>>()
    val isEmptyMessage = message.map {
        it.isEmpty()
    }
    fun init(contact: Contact) {
        viewModelScope.launch {
            messageMemory.observe(contact).collectLatest {
                message.postValue(it)
            }
        }
    }
}