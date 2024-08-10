package com.aman.base.helper.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.aman.base.helper.shared.BROADCAST_ACTION_LOGOUT

class LogoutReceiver(val listener: OnLogoutBroadcastListener) : BroadcastReceiver() {
    private val TAG = LogoutReceiver::class.java.simpleName

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        Log.i(TAG, "onReceive: $action")

        if (action == BROADCAST_ACTION_LOGOUT) {
            Log.i(TAG, "onReceive: $action")
            listener.onLogoutReceived()
        }
    }

    interface OnLogoutBroadcastListener {
        fun onLogoutReceived()
    }
}