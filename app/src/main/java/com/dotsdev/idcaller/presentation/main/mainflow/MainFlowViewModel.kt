package com.dotsdev.idcaller.presentation.main.mainflow

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.dotsdev.idcaller.appSettingState.AppTheme
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
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.distinctUntilChanged

class MainFlowViewModel(
    private val contactMemory: ContactMemory,
    private val callMemory: CallMemory,
    private val messageMemory: MessageMemory,
    private val spamMessageMemory: SpamMessageMemory,
    private val roomRepository: RoomRepository,
    private val detectSpamMessage: DetectSpamMessage,
    private val getMessageLog: GetMessageLog,
    private val appTheme: AppTheme,
    private val userUsecase: UserUsecase
) : BaseViewModel() {
    val user = MutableLiveData<User>()

    override fun onStart(owner: LifecycleOwner) {
        viewModelScope.launch {
            userUsecase.getUser()?.let(user::postValue)
        }
        user.postValue(User(phoneNumber = "0352669370", name = ""))
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val detectedMessageSpam = getSpamMessage().map { it.messageId }
            getMessageLog.observeMessage()
                .distinctUntilChanged()
                .collect { messages ->
                    messages
                        .map {
                            val isSpam = if (detectedMessageSpam.contains(it.messageId)) {
                                true
                            } else {
                                detectSpamMessage(it.content)
                            }
                            it.copy(isSpam = isSpam)
                        }
                        .filter { it.isSpam }
                        .also(spamMessageMemory::set)
                        .forEach { insertMessageDao(it) }
                }
        }
    }

    private fun insertMessageDao(message: Message) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                roomRepository.spamMessageDao().insert(message)
            }
        }
    }

    private suspend fun getSpamMessage(): List<Message> = withContext(Dispatchers.IO) {
        roomRepository.spamMessageDao()
            .getMessages()
            ?.filterNotNull()
            .orEmpty()
    }

    fun setContactMemory(contacts: List<Contact>) {
        contactMemory.set(contacts)
    }

    fun setCallLogMemory(calls: List<Call>) {
        callMemory.set(calls)
    }

    fun setMessageMemory(messages: List<Message>) {
        messageMemory.set(messages)
    }

    fun setSimInfo(sims: List<SimInfo>) = viewModelScope.launch {
        val simInfo = (userUsecase.getSimInfo() ?: listOf())
        if (sims != simInfo) {
            userUsecase.setSimInfo(sims)
        }
    }

    fun setAppTheme(isEnableNightMode: Boolean) {
        appTheme.set(isEnableNightMode)
    }
}
