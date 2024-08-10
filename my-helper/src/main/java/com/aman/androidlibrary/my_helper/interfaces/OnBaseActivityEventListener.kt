package com.aman.androidlibrary.my_helper.interfaces

interface OnBaseActivityEventListener {
    fun onInternetStateChanged(isAvailable: Boolean)

    fun onBluetoothStateChanged(isOn: Boolean)
}