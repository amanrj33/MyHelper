package com.aman.base.helper.support.thread

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object ThreadHandler {
    val TAG = ThreadHandler.javaClass.simpleName

    fun runOnIO(block: suspend () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            block()
        }
    }

    fun runOnMain(block: suspend () -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            block()
        }
    }

    fun runOn(dispatcher: CoroutineDispatcher, block: suspend () -> Unit) {
        CoroutineScope(dispatcher).launch {
            block()
        }
    }
}