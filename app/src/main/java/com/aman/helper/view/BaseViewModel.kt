package com.aman.helper.view

import androidx.activity.ComponentActivity
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.aman.helper.data.DataHandler
import com.aman.helper.data.DataState
import com.aman.helper.support.rx.Disposable

open class BaseViewModel : ViewModel() {
    open val TAG = BaseViewModel::class.java.simpleName

    val loadingLiveData = MediatorLiveData(false)
    val messageLiveData = MediatorLiveData("")
    val errorLiveData = MediatorLiveData<Exception>()

    open fun initProps() {

    }

    fun <T> createObserver(action: (json: T) -> Unit): Observer<Disposable<DataState>> {
        return Observer { disposable ->
            com.aman.helper.data.DataHandler.handleResponse<T>(
                disposable,
                loadingLiveData,
                messageLiveData,
                errorLiveData
            ) {
                action(it)
            }
        }
    }

    fun showMessage(message: String) {
        if (message.isNotEmpty())
            messageLiveData.postValue(message)
    }

    @CallSuper
    open fun destroy(activity: ComponentActivity) {
        activity.viewModelStore.clear()
    }

    @CallSuper
    open fun destroy(fragment: Fragment) {
        fragment.viewModelStore.clear()
    }
}