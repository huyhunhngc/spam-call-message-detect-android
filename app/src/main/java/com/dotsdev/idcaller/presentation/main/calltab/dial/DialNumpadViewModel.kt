package com.dotsdev.idcaller.presentation.main.calltab.dial

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.dotsdev.idcaller.core.SingleLiveEvent
import com.dotsdev.idcaller.core.base.BaseViewModel
import kotlinx.coroutines.delay

class DialNumpadViewModel : BaseViewModel() {
    val numberField = MutableLiveData("")
    val cursor = numberField.map {
        it.length
    }
    var deleteJob = false
    val cursorSelect = MutableLiveData<Int>()

    val onClickDial = SingleLiveEvent<String>()

    fun addNumberValue(value: Char) {
        val numberValue = numberField.value ?: ""
        numberField.postValue(numberValue + value)
    }

    fun onClickDelete() {
        val numberValue = numberField.value ?: ""
        numberField.postValue(numberValue.substring(0, numberValue.length - 1))
    }

    fun onLongDelete() {
        var numberValue = numberField.value ?: ""
        deleteJob = true
        viewModelScope.launch {
            while (numberValue.isNotEmpty()) {
                if (deleteJob) {
                    numberValue = numberValue.substring(
                        0,
                        numberValue.length - 1
                    )
                } else {
                    return@launch
                }
                numberField.postValue(numberValue)
                delay(100)
            }
        }
    }

    fun onClickDial() {
        onClickDial.postValue(numberField.value ?: "")
    }
}