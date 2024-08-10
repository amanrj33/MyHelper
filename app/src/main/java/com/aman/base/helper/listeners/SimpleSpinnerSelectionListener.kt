package com.aman.base.helper.listeners

import android.util.Log
import android.view.View
import android.widget.AdapterView

open class SimpleSpinnerSelectionListener: AdapterView.OnItemSelectedListener {
    private val TAG = SimpleSpinnerSelectionListener::class.java.simpleName

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Log.e(TAG, "onNothingSelected: ")
    }
}