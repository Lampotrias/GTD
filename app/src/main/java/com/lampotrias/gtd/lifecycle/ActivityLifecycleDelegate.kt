package com.lampotrias.gtd.lifecycle

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class ActivityLifecycleDelegate : DefaultLifecycleObserver {
    private var isFirstCallResume = true

    private val listeners = mutableListOf<Listener>()

    fun addListener(listener: Listener) {
        listeners.add(listener)
    }

    @Suppress("unused")
    fun removeListener(listener: Listener) {
        listeners.remove(listener)
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        listeners.forEach { it.onCreate(owner) }
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)

        listeners.forEach { it.onStart(owner) }
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)

        listeners.forEach { it.onResume(owner, isFirstCallResume) }

        isFirstCallResume = false
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)

        listeners.forEach { it.onPause(owner) }
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)

        listeners.forEach { it.onStop(owner) }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)

        listeners.forEach { it.onDestroy(owner) }
    }

    interface Listener {
        fun onCreate(owner: LifecycleOwner) = Unit

        fun onStart(owner: LifecycleOwner) = Unit

        fun onResume(
            owner: LifecycleOwner,
            isFirstCall: Boolean,
        ) = Unit

        fun onPause(owner: LifecycleOwner) = Unit

        fun onStop(owner: LifecycleOwner) = Unit

        fun onDestroy(owner: LifecycleOwner) = Unit
    }
}
