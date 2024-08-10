package com.aman.helper.interfaces

interface OnBaseActivityEventListener {
    fun onInternetStateChanged(isAvailable: Boolean)

    fun onBluetoothStateChanged(isOn: Boolean)
}