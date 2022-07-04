package com.dotsdev.idcaller.core

import androidx.annotation.MainThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

interface LoadingInterface {

    @MainThread
    fun on()

    @MainThread
    fun off()
}

class Loading : LoadingInterface, StateFlow<Boolean> {

    private val impl: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private var count: Int = 0

    suspend operator fun <T> invoke(block: suspend () -> T): T {
        withContext(Dispatchers.Main) { on() }

        try {
            return coroutineScope { block() }
        } finally {
            withContext(Dispatchers.Main) { off() }
        }
    }

    override fun on() {
        ++count
        updateValue()
    }

    override fun off() {
        --count
        updateValue()
    }

    private fun updateValue() {
        impl.value = count > 0
    }

    override val replayCache: List<Boolean>
        get() = impl.replayCache

    override val value: Boolean
        get() = impl.value

    @InternalCoroutinesApi
    override suspend fun collect(collector: FlowCollector<Boolean>): Nothing {
        impl.collect(collector)
    }

    fun justOnceOff(): OneTimeOffLoadingWrapper =
        OneTimeOffLoadingWrapper(this)
}

class OneTimeOffLoadingWrapper(
    private val loading: LoadingInterface
) : LoadingInterface {

    private var beforeTurnedOn: Boolean = true
    private var beforeTurnedOff: Boolean = true

    override fun on() {
        check(beforeTurnedOff)

        if (beforeTurnedOn) {
            loading.on()
            beforeTurnedOn = false
        }
    }

    override fun off() {
        check(!beforeTurnedOn)

        if (beforeTurnedOff) {
            loading.off()
            beforeTurnedOff = false
        }
    }
}
