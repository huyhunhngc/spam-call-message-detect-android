package com.dotsdev.idcaller.presentation.main.calltab

import androidx.lifecycle.MutableLiveData
import com.dotsdev.idcaller.core.SingleLiveEvent
import com.dotsdev.idcaller.core.base.BaseViewModel
import com.dotsdev.idcaller.data.model.toCallGroup
import com.dotsdev.idcaller.domain.contact.query.GetCallLog
import com.dotsdev.idcaller.domain.contact.query.GetRecentContact
import com.dotsdev.idcaller.widget.recycler.ContactMessageInfo
import com.dotsdev.idcaller.widget.recycler.toInfoData
import kotlinx.coroutines.flow.collectLatest

class CallListViewModel(
    private val getCallLog: GetCallLog,
    private val getRecentContact: GetRecentContact,
) : BaseViewModel() {
    val callLog = SingleLiveEvent<List<ContactMessageInfo>>()
    val recentContact = SingleLiveEvent<List<ContactMessageInfo>>()
    val callClick = SingleLiveEvent<ContactMessageInfo>()
    init {
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
    val onToDetailClick: ((info: ContactMessageInfo, position: Int) -> Unit) = { info, _ ->
        callClick.postValue(info)
    }
}
