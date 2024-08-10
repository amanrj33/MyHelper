package com.aman.androidlibrary.my_helper.view.adapters.spinner

import android.content.Context
import android.widget.ArrayAdapter

open class BaseSpinnerAdapter<T>(context: Context, items: ArrayList<T>): ArrayAdapter<T>(context, resource, items) {
    companion object {
        private const val resource = android.R.layout.simple_spinner_dropdown_item
    }
}