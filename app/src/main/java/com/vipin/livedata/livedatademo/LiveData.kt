package com.vipin.livedata.livedatademo

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import java.util.*
import kotlin.collections.HashMap

/**
 * Created by vipin.c on 15/05/2019
 */
class LiveData<T> {

    private var mValue: T? = null
    private val mObservers: HashMap<(T?) -> Unit, LiveDataLifeCycleObserver> = HashMap()

    fun setValue(value: T) {
        mValue = value

        for (lifecycleObserver in mObservers.values) {
            val owner = lifecycleObserver.owner
            if (owner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                notifyChanges(lifecycleObserver)
            }
        }
    }

    fun getValue(): T? {
        return mValue
    }

    fun addObserver(observer: (T?) -> Unit, owner: LifecycleOwner) {
        val lifecycleObserver = LiveDataLifeCycleObserver(owner, observer)
        mObservers[observer] = lifecycleObserver
        owner.lifecycle.addObserver(lifecycleObserver)
    }

    fun removeObserver(observer: (T?) -> Unit) {
        val lifecycleObserver = mObservers.remove(observer)
        lifecycleObserver?.let { lifecycleObserver.owner.lifecycle.removeObserver(it) }
    }

    private inner class LiveDataLifeCycleObserver(val owner: LifecycleOwner, val observer: (T?) -> Unit) : LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        fun onStart() {
            notifyChanges(this)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun onResume() {
            notifyChanges(this)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            notifyChanges(this)
        }
    }

    private fun notifyChanges(liveDataLifeCycleObserver: LiveDataLifeCycleObserver) {
        liveDataLifeCycleObserver.observer.invoke(mValue)
    }
}