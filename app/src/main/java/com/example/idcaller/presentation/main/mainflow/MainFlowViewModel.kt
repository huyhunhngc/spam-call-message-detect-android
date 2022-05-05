package com.example.idcaller.presentation.main.mainflow

import com.example.idcaller.core.base.BaseViewModel
import com.example.idcaller.data.memory.contact.ContactMemory
import com.example.idcaller.data.model.Contact

class MainFlowViewModel(private val contactMemory: ContactMemory) : BaseViewModel() {
    fun setContactMemory(contacts: List<Contact>) {
        contactMemory.set(contacts)
    }
}