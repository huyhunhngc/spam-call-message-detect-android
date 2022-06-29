package com.dotsdev.idcaller.presentation.main.calltab

import androidx.lifecycle.MutableLiveData
import com.dotsdev.idcaller.core.base.BaseViewModel
import com.dotsdev.idcaller.data.memory.contact.ContactMemory
import com.dotsdev.idcaller.data.model.CallGroup
import com.dotsdev.idcaller.widget.recycler.ContactMessageInfo
import com.dotsdev.idcaller.widget.recycler.toInfoData

class CallDetailViewModel(
    private val callGroup: CallGroup,
    private val contactMemory: ContactMemory
) : BaseViewModel() {
    val detailCall = MutableLiveData<ContactMessageInfo>()
    val number = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val contactInfo = MutableLiveData<ContactMessageInfo>()

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
            loading {
                //TODO: check API
            }
        }
    }

}