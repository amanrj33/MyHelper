package com.aman.helper.support.rx.livedata

import androidx.lifecycle.LiveData

class SkipFirstLoadLiveData<T>: LiveData<T>() {
    private var isFirstLoad = true

    override fun postValue(value: T) {
        if (isFirstLoad) {
            isFirstLoad = false
            return
        }

        super.postValue(value)
    }

    override fun setValue(value: T) {
        if (isFirstLoad) {
            isFirstLoad = false
            return
        }

        super.setValue(value)
    }
}