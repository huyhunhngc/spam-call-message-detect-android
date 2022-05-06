package com.example.idcaller.presentation.main.mainflow

import androidx.lifecycle.MutableLiveData
import com.example.idcaller.core.base.BaseViewModel
import com.example.idcaller.data.memory.contact.ContactMemory
import com.example.idcaller.data.model.Contact
import com.example.idcaller.data.model.User

class MainFlowViewModel(private val contactMemory: ContactMemory) : BaseViewModel() {
    val user = MutableLiveData<User>()
    override fun onStart() {
        user.postValue(User(phoneNumber = "0326708983", name = "Huy hn"))
    }
    fun setContactMemory(contacts: List<Contact>) {
        contactMemory.set(contacts)
    }
}