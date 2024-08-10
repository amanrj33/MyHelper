package com.aman.helper.support.rx.livedata

import androidx.lifecycle.Observer

abstract class SkipFirstLoadObserver<T>: Observer<T> {
    private var isFirstLoad = true

    override fun onChanged(t: T) {
        if (isFirstLoad) {
            isFirstLoad = false
            return
        }

        onSkipFirstLoadChanged(t)
    }

    abstract fun onSkipFirstLoadChanged(t: T)
}