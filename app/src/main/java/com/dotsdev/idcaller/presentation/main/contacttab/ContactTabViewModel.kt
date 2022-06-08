package com.dotsdev.idcaller.presentation.main.contacttab

import androidx.lifecycle.MutableLiveData
import com.dotsdev.idcaller.core.base.BaseViewModel
import com.dotsdev.idcaller.data.memory.contact.ContactMemory
import com.dotsdev.idcaller.widget.recycler.ContactMessageInfo
import com.dotsdev.idcaller.widget.recycler.toInfoData
import kotlinx.coroutines.flow.collectLatest

class ContactTabViewModel(private val contactMemory: ContactMemory) : BaseViewModel() {
    val listContact = MutableLiveData<List<ContactMessageInfo>>()
    override fun onStart() {
        super.onStart()
        viewModelScope.launch {
            contactMemory.observe().collectLatest {
                listContact.postValue(it.map { it.toInfoData() })
            }
        }
    }

    val onItemClick: ((info: ContactMessageInfo, position: Int) -> Unit) = { info, position ->
        // TODO
    }
}