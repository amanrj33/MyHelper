package com.aman.base.helper.interfaces

import androidx.activity.result.ActivityResult
import androidx.annotation.CallSuper
import com.aman.base.helper.data.DataState
import org.json.JSONObject

interface BaseActivityFunctions {

    fun initProps() {}

    fun addListeners() {}

    @CallSuper
    fun addObservers() {
    }

    fun setDataToUi() {}

    fun renderBlockingUi() {}

    fun getInitialData() {}

    fun onApiResponse(fromApi: Int, response: DataState) {}


    @CallSuper
    fun onApiLoading(fromApi: Int) {
    }

    @CallSuper
    fun onHandleResponse(fromApi: Int, dataJson: JSONObject?) {
    }

    @CallSuper
    fun onHandleError(fromApi: Int, error: String?) {
    }

    fun onActivityResult(activityResult: ActivityResult) {}
}