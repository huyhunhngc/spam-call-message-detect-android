package com.dotsdev.idcaller.presentation.main.calltab.dial

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.dotsdev.idcaller.core.SingleLiveEvent
import com.dotsdev.idcaller.core.base.BaseViewModel
import kotlinx.coroutines.delay

class DialNumpadViewModel : BaseViewModel() {
    val numberField = MutableLiveData("")
    val isVisibleNumberField = numberField.map {
        it.isNullOrEmpty().not()
    }
    val cursor = MutableLiveData(0)
    var deleteJob = false

    val onClickDial = SingleLiveEvent<String>()
    val triggerAddNumber = SingleLiveEvent<Char>()

    fun addNumberValueTrigger(value: Char) {
        triggerAddNumber.postValue(value)
    }

    fun addNumberValue(value: Char, position: Int) {
        val numberValue = numberField.value ?: ""
        if (numberValue.isEmpty() || position == 0) {
            numberField.postValue(numberValue + value)
        } else {
            val afterValue = kotlin.runCatching {
                numberValue.substring(position, numberValue.length)
            }.getOrDefault("")
            val beforeValue = kotlin.runCatching {
                numberValue.substring(0, position)
            }.getOrDefault("")
            numberField.postValue(beforeValue + value + afterValue)
        }
        //cursor.postValue(position)
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