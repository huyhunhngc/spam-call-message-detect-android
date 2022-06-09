package com.dotsdev.idcaller.presentation.main.mainflow

import android.provider.CallLog
import androidx.lifecycle.MutableLiveData
import com.dotsdev.idcaller.core.base.BaseViewModel
import com.dotsdev.idcaller.data.local.PreferencesDataSource
import com.dotsdev.idcaller.data.memory.contact.CallMemory
import com.dotsdev.idcaller.data.memory.contact.ContactMemory
import com.dotsdev.idcaller.data.model.Call
import com.dotsdev.idcaller.data.model.Contact
import com.dotsdev.idcaller.data.model.User
import com.dotsdev.idcaller.usecase.UserUsecase

class MainFlowViewModel(
    private val contactMemory: ContactMemory,
    private val callMemory: CallMemory,
    private val userUsecase: UserUsecase
) : BaseViewModel() {
    val currentTab = MutableLiveData(PageTabType.NAV_CONTACT)
    val user = MutableLiveData<User>()
    override fun onStart() {
        viewModelScope.launch {
            userUsecase.getUser()?.let(user::postValue)
        }
        //TODO: mock
        user.postValue(User(phoneNumber = "0326708983", name = "Huy hn"))
    }

    fun setContactMemory(contacts: List<Contact>) {
        contactMemory.set(contacts)
    }

    fun setCallLogMemory(calls: List<Call>) {
        callMemory.set(calls)
    }
}