package com.dotsdev.idcaller.presentation.main.initial

import androidx.lifecycle.MutableLiveData
import com.dotsdev.idcaller.core.base.BaseViewModel
import com.dotsdev.idcaller.data.local.CacheDataSource

class InitialViewModel(private val cacheDataSource: CacheDataSource) : BaseViewModel() {

    val onGrantPermissionCompleted = MutableLiveData<Unit>()

    fun navigateToDest(firstAction: () -> Unit, normalAction: () -> Unit) {
        val isLogged = true
        if (isLogged) {
            firstAction()
        } else {
            normalAction()
        }
    }

    fun setHasGrantedPermission(granted: Boolean) {
        if (granted) {
            onGrantPermissionCompleted.postValue(Unit)
        }
        cacheDataSource.setIsNeededGrantPermission(!granted)
    }
}
