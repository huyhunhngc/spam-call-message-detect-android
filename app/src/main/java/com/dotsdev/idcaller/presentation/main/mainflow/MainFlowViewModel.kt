package com.dotsdev.idcaller.presentation.main.mainflow

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.dotsdev.idcaller.core.base.BaseViewModel
import com.dotsdev.idcaller.data.dao.RoomRepository
import com.dotsdev.idcaller.data.memory.contact.CallMemory
import com.dotsdev.idcaller.data.memory.contact.ContactMemory
import com.dotsdev.idcaller.data.memory.message.MessageMemory
import com.dotsdev.idcaller.data.memory.message.SpamMessageMemory
import com.dotsdev.idcaller.data.model.*
import com.dotsdev.idcaller.domain.detectSpam.DetectSpamMessage
import com.dotsdev.idcaller.domain.message.query.GetMessageLog
import com.dotsdev.idcaller.usecase.UserUsecase
import com.dotsdev.idcaller.utils.toVietnamese
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext

class MainFlowViewModel(
    private val contactMemory: ContactMemory,
    private val callMemory: CallMemory,
    private val messageMemory: MessageMemory,
    private val spamMessageMemory: SpamMessageMemory,
    private val roomRepository: RoomRepository,
    private val detectSpamMessage: DetectSpamMessage,
    private val getMessageLog: GetMessageLog,
    private val userUsecase: UserUsecase
) : BaseViewModel() {
    val user = MutableLiveData<User>()
    override fun onStart() {
        viewModelScope.launch {
            userUsecase.getUser()?.let(user::postValue)
        }
        // TODO: mock
        user.postValue(User(phoneNumber = "0352669370", name = "Văn Huy"))
    }

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(EmptyCoroutineContext).launch(Dispatchers.IO) {
            getMessageLog.observeMessage().collect { messages ->
                messages.map {
                    it.copy(isSpam = detectSpamMessage(it.content) && it.sentByMe.not())
                }.let {
                    spamMessageMemory.set(it)
                    it.forEach {
                        kotlin.runCatching {
                            roomRepository.spamMessageDao().insert(it)
                        }
                    }
                }
            }
        }
        viewModelScope.launch {
            roomRepository.spamMessageDao().getMessages().let {
                spamMessageMemory.set(it?.mapNotNull { it } ?: listOf())
            }
        }
    }

    fun setContactMemory(contacts: List<Contact>) {
        viewModelScope.launch {
            contactMemory.set(contacts)
        }
    }

    fun setCallLogMemory(calls: List<Call>) {
        viewModelScope.launch {
            callMemory.set(calls)
        }
    }

    fun setMessageMemory(messages: List<Message>) {
        viewModelScope.launch {
            messageMemory.set(messages)
        }
    }

    fun setSimInfo(sims: List<SimInfo>) = viewModelScope.launch {
        val simInfo = (userUsecase.getSimInfo() ?: listOf())
        if (sims != simInfo) {
            userUsecase.setSimInfo(sims)
        }
    }
}
