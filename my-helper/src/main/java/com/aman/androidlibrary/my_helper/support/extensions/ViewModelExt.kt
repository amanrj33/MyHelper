package com.aman.androidlibrary.my_helper.support.extensions

import android.util.Log
import androidx.lifecycle.ViewModel
import com.aman.androidlibrary.my_helper.data.DataState
import com.aman.androidlibrary.my_helper.data.DataStatus
import com.google.gson.Gson
import org.json.JSONObject
import java.net.UnknownHostException

const val TAG = "ViewModelExt"

object Instances {
    val gson: Gson by lazy { Gson() }
}

fun ViewModel.gson(): Gson = Instances.gson

fun ViewModel.onApiResponse(fromApi: Int, response: DataState) {
    when (response.status) {
        DataStatus.LOADING -> {
            onApiLoading(fromApi)
        }

        DataStatus.LOADED -> {
            JSONObject(Instances.gson.toJson(response.data)).let {
                Log.i(TAG, "onApiResponse: $it")
                if (it.optInt("code") == 200) {
                    onHandleResponse(fromApi, it.optJSONObject("data"))
                } else
                    onHandleError(fromApi, it.optString("message"))
            }
        }

        else -> {
            response.error?.let {
                Log.i(TAG, "onApiResponse: $it")
                var message = ""
                when (it) {
                    is UnknownHostException -> message = "Not connected to internet"
                    else -> message =
                        it.localizedMessage ?: "We're sorry! Something went wrong."
                }
                onHandleError(fromApi, message)
            }
        }
    }
}

fun ViewModel.onApiLoading(fromApi: Int) {
    Log.v(TAG, "onApiLoading: fromApi=$fromApi")
}

fun ViewModel.onHandleResponse(fromApi: Int, dataJson: JSONObject?) {
    Log.i(TAG, "onHandleResponse: fromApi=$fromApi")
}

fun ViewModel.onHandleError(fromApi: Int, error: String?) {
    Log.e(TAG, "onHandleError: fromApi=$fromApi")
}