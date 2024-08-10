package com.aman.base.helper.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.aman.base.helper.support.rx.Disposable
import com.google.gson.JsonElement
import org.json.JSONObject

object DataHandler {
    val TAG = DataHandler.javaClass.simpleName
    private var token = ""

    fun <T> handleResponse(
        responseJson: JsonElement?,
        messageLiveData: MutableLiveData<String>,
        onResult: (result: T) -> Unit,
    ) {
        responseJson?.let { response ->
            try {
                JSONObject(response.toString()).let { jsonResponse ->
                    Log.i(TAG, "handleResponse: $jsonResponse")
                    if (jsonResponse.optInt("code") == 200) {
                        onResult(jsonResponse.opt("data") as T)
                    } else {
                        messageLiveData.postValue(jsonResponse.optString("message"))
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "handleResponse: ", e)
            }
        }
    }

    fun <T> handleResponse(
        disposable: Disposable<DataState>,
        loadingLiveData: MutableLiveData<Boolean>,
        messageLiveData: MutableLiveData<String>,
        errorLiveData: MutableLiveData<Exception>,
        onResult: (result: T) -> Unit,
    ) {
        disposable.getContentIfNotHandled()?.let { apiState ->
            when (apiState.status) {
                DataStatus.LOADING -> loadingLiveData.postValue(true)
                DataStatus.LOADED -> {
                    apiState.data?.let { response ->
                        handleResponse(response, messageLiveData, onResult)
                    }
                    loadingLiveData.postValue(false)
                }

                DataStatus.FAILED -> {
                    Log.e(TAG, "handleResponse: ", apiState.error)
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