package com.vipin.livedata.livedatademo

import java.util.*

/**
 * Created by vipin.c on 15/05/2019
 */
class LiveData<T> {

    private var mValue: T? = null
    private val mObservers = arrayListOf<(T?) -> Unit>()

    fun setValue(value: T) {
        mValue = value

        for (observer in mObservers) {
            observer.invoke(mValue)
        }
    }

    fun getValue(): T? {
        return mValue
    }

    fun addObserver(observer: (T?) -> Unit){
        mObservers.add(observer)
    }

    fun removeObserver(observer: (T?) -> Unit){
        mObservers.remove(observer)
    }
}