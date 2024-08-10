package com.aman.helper.view.controller

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.WindowInsetsController
import androidx.activity.result.ActivityResult
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewbinding.ViewBinding
import com.aman.helper.data.DataState
import com.aman.helper.interfaces.BaseActivityEventInvoker
import com.aman.helper.interfaces.BaseActivityFunctions
import com.aman.helper.interfaces.OnBroadcastsListener
import com.aman.helper.receivers.LogoutReceiver
import com.aman.helper.shared.BROADCAST_ACTION_LOGOUT
import com.aman.helper.support.rx.Disposable
import com.google.android.material.appbar.MaterialToolbar
import com.google.gson.Gson
import pub.devrel.easypermissions.EasyPermissions

abstract class BaseActivity<Binding : ViewBinding> : AppCompatActivity(),
    EasyPermissions.PermissionCallbacks,
    SwipeRefreshLayout.OnRefreshListener,
    BaseActivityEventInvoker,
    BaseActivityFunctions,
    OnBroadcastsListener {
    open val TAG = BaseActivity::class.java.simpleName

    var permissionCode = 2023

    val gson = Gson()
    val viewHandler = Handler(Looper.getMainLooper())

    lateinit var binding: Binding

    val logoutReceiver = LogoutReceiver(this)

    abstract fun getMyBinding(): Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = getMyBinding()
        setContentView(binding.root)
    }

    open fun modifyWindowProps() {
//        // Get the WindowInsetsController
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window?.insetsController?.let {
                // For light theme, set status bar icons and text to dark
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    it.setSystemBarsAppearance(
                        0,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                    )
                }
                // For dark theme, clear the appearance to set status bar icons and text to light
                else {
                    it.setSystemBarsAppearance(
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                    )
                }
            }
        }
    }

    override fun setContentView(view: View?) {
        super.setContentView(view)

        initProps()
        addListeners()
        addObservers()
        setDataToUi()
        getInitialData()
    }

    fun observeApi(liveData: MutableLiveData<Disposable<DataState>>, fromApi: Int) {
        liveData.observe(this) {
            it.getContentIfNotHandled()?.let { r -> onApiResponse(fromApi, r) }
        }
    }

    open fun setUpToolbar(toolbar: MaterialToolbar, title: String) {
        toolbar.title = title
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onShowProgress() {}

    override fun onHideProgress() {}

    override fun requestPermissions(code: Int, message: String, vararg permissions: String) {
        if (code != permissionCode) {
            permissionCode = code
        }
        EasyPermissions.requestPermissions(this, message, code, *permissions)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.i(TAG, "onRequestPermissionsResult: $permissions")
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String?>) {
        Log.i(TAG, "onPermissionsGranted: $perms")
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String?>) {
        Log.i(TAG, "onPermissionsDenied: $perms")
    }

    override fun onRefresh() {

    }

    override fun onLogoutReceived() {
        Log.i(TAG, "onLogoutReceived: ")
    }

    override fun onStart() {
        super.onStart()

        LocalBroadcastManager.getInstance(applicationContext)
            .registerReceiver(logoutReceiver, IntentFilter(BROADCAST_ACTION_LOGOUT))
    }

    override fun onDestroy() {
        super.onDestroy()

        LocalBroadcastManager.getInstance(applicationContext)
            .unregisterReceiver(logoutReceiver)
    }
}