package com.dotsdev.idcaller.presentation.main.calltab

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.dotsdev.idcaller.core.base.BaseViewModel
import com.dotsdev.idcaller.data.memory.contact.CallMemory
import com.dotsdev.idcaller.data.memory.contact.ContactMemory
import com.dotsdev.idcaller.data.model.Contact
import com.dotsdev.idcaller.widget.recycler.ContactMessageInfo
import com.dotsdev.idcaller.widget.recycler.toInfoData
import kotlinx.coroutines.flow.collectLatest

class CallTabViewModel(
    private val callMemory: CallMemory,
    private val contactMemory: ContactMemory
) : BaseViewModel() {
    var contacts: List<Contact> = listOf()
    val callLog = MutableLiveData<List<ContactMessageInfo>>()
    override fun onStart() {
        super.onStart()
        viewModelScope.launch {
            contacts = contactMemory.get()
            callMemory.observe().collectLatest { calls ->
                calls.map { call ->
                    val contact = contacts.find { call.callerNumber == it.phoneNumber }
                    call.copy(
                        contact = contact ?: Contact(
                            phoneNumber = call.callerNumber,
                            callerName = call.callerNumber
                        )
                    )
                }.let {
                    Log.d("!@#", "onStart: $it")
                    callMemory.set(it)
                    callLog.postValue(it.map { it.toInfoData() })
                }
            }
        }
    }
}