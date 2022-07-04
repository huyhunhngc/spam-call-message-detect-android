package com.dotsdev.idcaller.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dotsdev.idcaller.core.Loading
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseViewModel :
    ViewModel(),
    WithLifecycleScope {

    final override val observer: LifecycleScope by lifecycleScope()
    val loading: Loading = Loading()
    protected val _error: MutableSharedFlow<Throwable?> = MutableSharedFlow()
    val error: SharedFlow<Throwable?> =
        _error.shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    val viewModelScope: CoroutineScope
        get() {
            return (this as ViewModel).viewModelScope
        }

    protected fun CoroutineScope.launch(
        onError: ((throwable: Throwable) -> Unit)? = null,
        context: CoroutineContext = EmptyCoroutineContext,
        block: suspend CoroutineScope.() -> Unit,
    ): Job {
        return launch(context = context, start = CoroutineStart.DEFAULT) {
            try {
                block()
            } catch (throwable: Throwable) {
                beforeErrorHandel(throwable)

                if (onError != null) {
                    onError(throwable)
                } else {
                    _error.emit(throwable)
                }
            }
        }
    }

    open fun beforeErrorHandel(throwable: Throwable) {
    }

    suspend fun <T> Flow<T>.collectLatestWithLoading(collectLatest: suspend (result: T) -> Unit) {
        val oneTimeOffLoading = loading.justOnceOff()
        this.onStart {
            oneTimeOffLoading.on()
        }.onCompletion {
            oneTimeOffLoading.off()
        }.collectLatest { result ->
            collectLatest(result)
            oneTimeOffLoading.off()
        }
    }
}
