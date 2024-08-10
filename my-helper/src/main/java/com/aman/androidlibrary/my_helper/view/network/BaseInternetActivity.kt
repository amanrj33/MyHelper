package com.aman.androidlibrary.my_helper.view.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.viewbinding.ViewBinding
import com.aman.androidlibrary.my_helper.shared.Store
import com.aman.androidlibrary.my_helper.view.controller.BaseActivity
import pub.devrel.easypermissions.EasyPermissions

abstract class BaseInternetActivity<Binding : ViewBinding> : BaseActivity<Binding>(),
    EasyPermissions.PermissionCallbacks {
    override val TAG = BaseInternetActivity::class.java.simpleName

    var isInternetAvailableMutable = MutableLiveData(true)
    val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            isInternetAvailableMutable.postValue(true)
            Log.i(TAG, "onAvailable: ")
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            isInternetAvailableMutable.postValue(false)
            Log.e(TAG, "onLost: ")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager)?.let {
            it.registerDefaultNetworkCallback(networkCallback)
            Log.i(TAG, "onCreate: ${it.activeNetwork}")
            isInternetAvailableMutable.postValue(it.activeNetwork != null)
        }
    }

    override fun addObservers() {
        super.addObservers()
        isInternetAvailableMutable.observe(this) {
            Store.isNetworkAvailable = it
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)?.let {
            it.unregisterNetworkCallback(networkCallback)
        }
    }
}