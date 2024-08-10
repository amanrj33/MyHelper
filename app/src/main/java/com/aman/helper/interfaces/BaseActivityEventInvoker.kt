package com.aman.helper.interfaces

interface BaseActivityEventInvoker {
    fun requestPermissions(code: Int, message: String = "", vararg permissions: String)

    fun onShowProgress()

    fun onHideProgress()
}