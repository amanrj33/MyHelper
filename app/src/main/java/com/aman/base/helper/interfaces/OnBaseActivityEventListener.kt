package com.aman.base.helper.interfaces

interface OnBaseActivityEventListener {
    fun onInternetStateChanged(isAvailable: Boolean)

    fun onBluetoothStateChanged(isOn: Boolean)
}