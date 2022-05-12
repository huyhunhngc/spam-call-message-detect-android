package com.dotsdev.idcaller.core.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

interface LifecycleScope : LifecycleObserver

interface WithLifecycleScope {

    val observer: LifecycleScope

    fun onCreate() {}

    fun onStart() {}

    fun onResume() {}

    fun onPause() {}

    fun onStop() {}

    fun onDestroy() {}
}

private class LifecycleScopeImpl(
    private val target: WithLifecycleScope
) : LifecycleScope {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        target.onCreate()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        target.onStart()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        target.onResume()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        target.onPause()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        target.onStop()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        target.onDestroy()
    }
}

fun <T> lifecycleScope(): ReadOnlyProperty<T, LifecycleScope> where T : ViewModel, T : WithLifecycleScope =
    object : ReadOnlyProperty<T, LifecycleScope> {
        private var lifecycleScope: LifecycleScope? = null

        override fun getValue(thisRef: T, property: KProperty<*>): LifecycleScope =
            lifecycleScope
                ?: LifecycleScopeImpl(
                    thisRef
                ).also { lifecycleScope = it }
    }
