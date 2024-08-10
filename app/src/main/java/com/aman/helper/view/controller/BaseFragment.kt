package com.aman.helper.view.controller

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewbinding.ViewBinding
import com.aman.helper.data.DataState
import com.aman.helper.interfaces.BaseActivityEventInvoker
import com.aman.helper.interfaces.BaseActivityFunctions
import com.aman.helper.support.rx.Disposable
import com.google.gson.Gson

abstract class BaseFragment<Binding : ViewBinding>(var invoker: BaseActivityEventInvoker) :
    Fragment(), BaseActivityFunctions, SwipeRefreshLayout.OnRefreshListener {
    open val TAG = BaseFragment::class.java.simpleName

    var baseActivityEventInvoker: BaseActivityEventInvoker? = null

    val gson = Gson()
    val viewHandler = Handler(Looper.getMainLooper())
    var isThisFragmentVisibleInScreen = false

    lateinit var binding: Binding

    abstract fun getMyBinding(inflater: LayoutInflater, container: ViewGroup?): Binding

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is BaseActivityEventInvoker)
            baseActivityEventInvoker = context
        else
            throw RuntimeException("$context must implement BaseActivityListener")
    }

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
        addObservers()
        setDataToUi()
        getInitialData()
    }

    fun observeApi(liveData: MutableLiveData<Disposable<DataState>>, fromApi: Int) {
        liveData.observe(this) {
            it.getContentIfNotHandled()?.let { r -> onApiResponse(fromApi, r) }
        }
    }

    override fun onResume() {
        super.onResume()

        isThisFragmentVisibleInScreen = true
    }

    override fun onRefresh() {

    }

    override fun onPause() {
        super.onPause()

        isThisFragmentVisibleInScreen = false
    }

    override fun onDetach() {
        super.onDetach()
        baseActivityEventInvoker = null
    }
}