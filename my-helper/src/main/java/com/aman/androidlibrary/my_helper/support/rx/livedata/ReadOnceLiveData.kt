package com.aman.androidlibrary.my_helper.support.rx.livedata

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class ReadOnceLiveData<T>(value: T? = null): MutableLiveData<T>(value) {
    private val read = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner) { t ->
            if (read.compareAndSet(false, true)) {
                observer.onChanged(t)
            }
        }
    }

    @MainThread
    override fun setValue(t: T?) {
        read.set(true)
        super.setValue(t)
    }

    @MainThread
    override fun postValue(value: T) {
        read.set(true)
        super.postValue(value)
    }

    // Used for cases where T is Void, to make calls cleaner.
    @MainThread
    fun call() {
        value = null
    }
}