package com.dotsdev.idcaller.presentation.main.calltab

import androidx.lifecycle.MutableLiveData
import com.dotsdev.idcaller.core.base.BaseViewModel
import com.dotsdev.idcaller.data.memory.contact.ContactMemory
import com.dotsdev.idcaller.data.model.Call
import com.dotsdev.idcaller.data.model.CallGroup
import com.dotsdev.idcaller.domain.contact.query.GetCallLog
import com.dotsdev.idcaller.widget.recycler.ContactMessageInfo
import com.dotsdev.idcaller.widget.recycler.toInfoData
import kotlinx.coroutines.flow.collectLatest

class CallDetailViewModel(
    private val callGroup: CallGroup,
    private val getCallLog: GetCallLog,
    private val contactMemory: ContactMemory
) : BaseViewModel() {
    val detailCall = MutableLiveData<ContactMessageInfo>()
    val number = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val contactInfo = MutableLiveData<ContactMessageInfo>()

    val callList = MutableLiveData<List<Call>>(callGroup.calls)

    val contact = callGroup.calls.first().contact

    init {
        contactInfo.postValue(callGroup.toInfoData())
        callGroup.calls.first().run {
            number.postValue(callerNumber)
            name.postValue(contactMemory.get().find {
                callerNumber == it.phoneNumber
            }?.callerName ?: callerNumber)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModelScope.launch {
            getCallLog.observeCall().collectLatest {
                callList.postValue(
                    it.filter {
                        it.callerNumber == contact.phoneNumber
                    }.sortedByDescending { it.iat }
                )
            }
        }
    }

}