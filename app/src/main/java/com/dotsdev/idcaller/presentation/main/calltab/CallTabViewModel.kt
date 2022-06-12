package com.dotsdev.idcaller.presentation.main.calltab

import androidx.lifecycle.MutableLiveData
import com.dotsdev.idcaller.core.base.BaseViewModel
import com.dotsdev.idcaller.data.model.Call
import com.dotsdev.idcaller.domain.contact.query.GetCallLog
import com.dotsdev.idcaller.widget.recycler.ContactMessageInfo
import com.dotsdev.idcaller.widget.recycler.toInfoData
import kotlinx.coroutines.flow.collectLatest

class CallTabViewModel(
    private val getCallLog: GetCallLog
) : BaseViewModel() {
    val callLog = MutableLiveData<List<ContactMessageInfo>>()
    override fun onStart() {
        super.onStart()
        viewModelScope.launch {
            getCallLog.observeCall().collectLatest { calls ->
                callLog.postValue(calls.map { it.toInfoData() })
            }
        }
    }
}