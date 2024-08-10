package com.aman.androidlibrary.my_helper.models

import androidx.annotation.CallSuper
import org.json.JSONObject

open class BaseModel(
    var id: String = "",
) {
    @CallSuper
    open fun toJson(): JSONObject {
        val json = JSONObject()
        try {
            json.put("_id", id)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return json
    }

    @CallSuper
    open fun fromJson(jsonObject: JSONObject) {
        id = jsonObject.optString("_id")
    }

    override fun equals(other: Any?): Boolean {
        if (other !is BaseModel) return false
        return other.id == id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}