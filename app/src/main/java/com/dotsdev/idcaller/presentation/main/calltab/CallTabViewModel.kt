package com.dotsdev.idcaller.presentation.main.calltab

import androidx.lifecycle.MutableLiveData
import com.dotsdev.idcaller.core.base.BaseViewModel
import com.dotsdev.idcaller.data.model.toCallGroup
import com.dotsdev.idcaller.domain.contact.query.GetCallLog
import com.dotsdev.idcaller.domain.contact.query.GetRecentContact
import com.dotsdev.idcaller.widget.recycler.ContactMessageInfo
import com.dotsdev.idcaller.widget.recycler.toInfoData
import kotlinx.coroutines.flow.collectLatest

class CallTabViewModel(
    private val getCallLog: GetCallLog,
    private val getRecentContact: GetRecentContact,
) : BaseViewModel() {
    val callLog = MutableLiveData<List<ContactMessageInfo>>()
    val recentContact = MutableLiveData<List<ContactMessageInfo>>()
    override fun onCreate() {
        super.onCreate()
        viewModelScope.launch {
            getCallLog.observeCall().collectLatest { calls ->
                callLog.postValue(calls.toCallGroup().map { it.toInfoData() })
            }
        }
        viewModelScope.launch {
            getRecentContact.observeRecent().collectLatest { contacts ->
                recentContact.postValue(contacts.map { it.toInfoData() })
            }
        }
    }
}