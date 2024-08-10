package com.aman.helper.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.aman.helper.support.rx.Disposable
import com.google.gson.JsonElement
import org.json.JSONObject

object DataHandler {
    val TAG = com.aman.helper.data.DataHandler.javaClass.simpleName
    private var token = ""

    fun <T> handleResponse(
        responseJson: JsonElement?,
        messageLiveData: MutableLiveData<String>,
        onResult: (result: T) -> Unit,
    ) {
        responseJson?.let { response ->
            try {
                JSONObject(response.toString()).let { jsonResponse ->
                    Log.i(com.aman.helper.data.DataHandler.TAG, "handleResponse: $jsonResponse")
                    if (jsonResponse.optInt("code") == 200) {
                        onResult(jsonResponse.opt("data") as T)
                    } else {
                        messageLiveData.postValue(jsonResponse.optString("message"))
                    }
                }
            } catch (e: Exception) {
                Log.e(com.aman.helper.data.DataHandler.TAG, "handleResponse: ", e)
            }
        }
    }

    fun <T> handleResponse(
        disposable: Disposable<com.aman.helper.data.DataState>,
        loadingLiveData: MutableLiveData<Boolean>,
        messageLiveData: MutableLiveData<String>,
        errorLiveData: MutableLiveData<Exception>,
        onResult: (result: T) -> Unit,
    ) {
        disposable.getContentIfNotHandled()?.let { apiState ->
            when (apiState.status) {
                com.aman.helper.data.DataStatus.LOADING -> loadingLiveData.postValue(true)
                com.aman.helper.data.DataStatus.LOADED -> {
                    apiState.data?.let { response ->
                        com.aman.helper.data.DataHandler.handleResponse(
                            response,
                            messageLiveData,
                            onResult
                        )
                    }
                    loadingLiveData.postValue(false)
                }

                com.aman.helper.data.DataStatus.FAILED -> {
                    Log.e(com.aman.helper.data.DataHandler.TAG, "handleResponse: ", apiState.error)
                    apiState.error?.let {
//                        if (it is HttpException) {
//                            Log.e(TAG, "handleResponse: ${it.code()}")
//                            Log.e(TAG, "handleResponse: ${it.response()}")
//                            if (it.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
//                                AppManager.application?.let { application ->
//                                    Log.i(TAG, "handleResponse: broadcasting logout")
//                                    LocalBroadcastManager.getInstance(application)
//                                        .sendBroadcast(Intent(BROADCAST_ACTION_LOGOUT))
//                                }
//                            } else {
//                                errorLiveData.postValue(it)
//                            }
//                        } else {
//                            errorLiveData.postValue(it)
//                        }
                    }
                    loadingLiveData.postValue(false)
                }
            }
        }
    }
}