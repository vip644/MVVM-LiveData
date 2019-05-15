package com.vipin.livedata.livedatademo

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import java.util.*
import kotlin.collections.HashMap

/**
 * Created by vipin.c on 15/05/2019
 */
class LiveData<T> {

    private var mValue: T? = null
    private val mObservers: HashMap<(T?) -> Unit, LifecycleOwner> = HashMap()

    fun setValue(value: T) {
        mValue = value

        for ((observer, owner) in mObservers) {
            if (owner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)){
                observer.invoke(mValue)
            }
        }
    }

    fun getValue(): T? {
        return mValue
    }

    fun addObserver(observer: (T?) -> Unit, owner: LifecycleOwner){
        mObservers[observer] = owner
    }

    fun removeObserver(observer: (T?) -> Unit){
        mObservers.remove(observer)
    }
}