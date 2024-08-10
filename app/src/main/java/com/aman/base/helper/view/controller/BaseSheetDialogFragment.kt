package com.aman.base.helper.view.controller

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.lifecycle.MutableLiveData
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewbinding.ViewBinding
import com.aman.base.helper.data.DataState
import com.aman.base.helper.data.DataStatus
import com.aman.base.helper.interfaces.BaseActivityFunctions
import com.aman.base.helper.support.rx.Disposable
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import org.json.JSONObject
import pub.devrel.easypermissions.EasyPermissions

abstract class BaseSheetDialogFragment<T : ViewBinding> : BottomSheetDialogFragment(),
    BaseActivityFunctions,
    EasyPermissions.PermissionCallbacks, SwipeRefreshLayout.OnRefreshListener {
    open val TAG = BottomSheetDialogFragment::class.java.simpleName

    val gson = Gson()
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

    override fun onRefresh() {

    }

    override fun onPause() {
        super.onPause()

        isThisFragmentVisibleInScreen = false
    }

    fun requestPermissions(permission: String, code: Int = 2023, message: String = "") {
        EasyPermissions.requestPermissions(this, message, code, permission)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.i(TAG, "onRequestPermissionsResult: $permissions")
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String?>) {
        Log.i(TAG, "onPermissionsGranted: $perms")
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String?>) {
        Log.i(TAG, "onPermissionsDenied: $perms")
    }

    override fun onApiResponse(fromApi: Int, response: DataState) {
        super.onApiResponse(fromApi, response)

        when (response.status) {
            DataStatus.LOADING -> {
                onApiLoading(fromApi)
            }

            DataStatus.LOADED -> {
                JSONObject(gson.toJson(response.data)).let {
                    Log.i(TAG, "onApiResponse: $it")
                    if (it.optInt("code") == 200) {
                        showToast(it.optString("message"))
                        onHandleResponse(fromApi, it.optJSONObject("data"))
                    } else
                        onHandleError(fromApi, it.optString("message"))
                }
            }

            else -> {
                onHandleError(fromApi, response.error?.localizedMessage)
            }
        }
    }

    @CallSuper
    override fun onHandleError(fromApi: Int, error: String?) {
        super.onHandleError(fromApi, error)
        Log.e(TAG, "onHandleError: fromApi=$fromApi")
        error?.let { showToast(it) }
    }

    fun observeApi(liveData: MutableLiveData<Disposable<DataState>>, fromApi: Int) {
        liveData.observe(this) {
            it.getContentIfNotHandled()?.let { r -> onApiResponse(fromApi, r) }
        }
    }

    fun showSnackBar(message: String) {
        if (message.isNotEmpty()) {
            Snackbar.make(
                requireActivity().findViewById(android.R.id.content),
                message,
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    fun navigate(destination: Class<*>, bundle: Bundle? = null) {
        val intent = Intent(requireContext(), destination)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    fun navigateWithFinish(destination: Class<*>) {
        startActivity(Intent(requireContext(), destination))
        requireActivity().finish()
    }

    fun navigateFinishAffinity(destination: Class<*>) {
        startActivity(Intent(requireContext(), destination))
        requireActivity().finishAffinity()
    }
}