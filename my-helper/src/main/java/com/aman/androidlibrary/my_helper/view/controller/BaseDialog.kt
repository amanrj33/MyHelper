package com.aman.androidlibrary.my_helper.view.controller

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.aman.androidlibrary.my_helper.interfaces.BaseActivityFunctions


abstract class BaseDialog<T : ViewBinding>(private val context: Context) : Dialog(context),
    BaseActivityFunctions {
    open val TAG = BaseDialog::class.java.simpleName

    lateinit var binding: T

    abstract fun getMyBinding(): T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getMyBinding()
        setContentView(binding.root)
        setDialogAttributes()
    }

    private fun setDialogAttributes() {
        // Get the window of the dialog
        val window = window
        if (window != null) {
            // Set the layout parameters to match parent
            window.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            // Optionally, set gravity to center
            window.setGravity(Gravity.CENTER)
        }
    }

    override fun onStart() {
        super.onStart()

        initProps()
        addListeners()
        addObservers()
        setDataToUi()
        getInitialData()
    }
}