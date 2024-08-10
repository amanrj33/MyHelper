package com.aman.androidlibrary.my_helper.data

import com.google.gson.JsonElement
import com.aman.androidlibrary.my_helper.support.rx.Disposable

class DataState(
    val status: DataStatus,
    val data: JsonElement?,
    val error: Exception?
) {
    companion object {
        fun loading(): DataState {
            return DataState(DataStatus.LOADING, null, null)
        }

        fun loaded(data: JsonElement): DataState {
            return DataState(DataStatus.LOADED, data, null)
        }

        fun failed(error: Exception): DataState {
            return DataState(DataStatus.FAILED, null, error)
        }
    }

    override fun toString(): String {
        return "DataState(status=$status, data=$data, error=$error)"
    }

    fun asDisposable(): Disposable<DataState> {
        return Disposable(this)
    }
}