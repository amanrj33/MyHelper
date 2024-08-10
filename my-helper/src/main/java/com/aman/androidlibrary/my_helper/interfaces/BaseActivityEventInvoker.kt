package com.aman.androidlibrary.my_helper.interfaces

interface BaseActivityEventInvoker {
    fun requestPermissions(code: Int, message: String = "", vararg permissions: String)

    fun onShowProgress()

    fun onHideProgress()
}