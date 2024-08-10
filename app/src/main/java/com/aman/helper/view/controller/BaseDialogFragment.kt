package com.aman.helper.view.controller

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewbinding.ViewBinding
import com.aman.helper.interfaces.BaseActivityFunctions

abstract class BaseDialogFragment<T : ViewBinding> : DialogFragment(),
    BaseActivityFunctions,
    SwipeRefreshLayout.OnRefreshListener {
    open val TAG = BaseDialogFragment::class.java.simpleName

    val viewHandler = Handler(Looper.getMainLooper())
    var isThisFragmentVisibleInScreen = false

    lateinit var binding: T

    abstract fun getMyBinding(inflater: LayoutInflater, container: ViewGroup?): T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getMyBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initProps()
        addListeners()
    }

    override fun onStart() {
        super.onStart()

        addObservers()
        setDataToUi()
        getInitialData()
    }

    override fun onResume() {
        super.onResume()

        isThisFragmentVisibleInScreen = true
    }

    override fun onPause() {
        super.onPause()

        isThisFragmentVisibleInScreen = false
    }
}