package com.dotsdev.idcaller.presentation.main.initial

import com.dotsdev.idcaller.core.base.BaseViewModel

class InitialViewModel: BaseViewModel() {
    fun navigateToDest(firstAction: () -> Unit, normalAction: () -> Unit) {
        val isLogged = true
        if (isLogged) {
            firstAction()
        } else {
            normalAction()
        }
    }
}