package com.example.idcaller.presentation.main.initial

import com.example.idcaller.core.base.BaseViewModel

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