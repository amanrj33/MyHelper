package com.aman.helper.data.parsers

import com.aman.helper.models.BaseModel
import org.json.JSONArray
import org.json.JSONObject

object CommonParser {
    val TAG = CommonParser.javaClass.simpleName

    fun parseStringArray(stringsJson: JSONArray?): ArrayList<String> {
        val list = ArrayList<String>()
        stringsJson?.let {
            for (i in 0 until it.length()) {
                list.add(it.optString(i))
            }
        }
        return list
    }

    fun parseIntArray(intsJson: JSONArray?): ArrayList<Int> {
        val list = ArrayList<Int>()
        intsJson?.let {
            for (i in 0 until it.length()) {
                list.add(it.optInt(i))
            }
        }
        return list
    }

    fun <T : BaseModel> parseArray(rootArray: JSONArray?, clazz: Class<T>): ArrayList<T> {
        val list = ArrayList<T>()
        rootArray?.let {
            for (i in 0 until it.length()) {
                list.add(parseObject(it.optJSONObject(i), clazz))
            }
        }
        return list
    }

    fun <T : BaseModel> parseObject(rootJson: JSONObject?, clazz: Class<T>): T {
        val t = clazz.getDeclaredConstructor().newInstance()
        if (rootJson != null) {
            t.fromJson(rootJson)
        }
        return t
    }
}