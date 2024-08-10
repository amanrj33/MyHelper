package com.aman.base.helper.interfaces

interface BaseActivityEventInvoker {
    fun requestPermissions(code: Int, message: String = "", vararg permissions: String)

    fun onShowProgress()

    fun onHideProgress()
}