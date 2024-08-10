package com.aman.androidlibrary.my_helper.support.extensions

import org.json.JSONArray

fun <T> JSONArray.toPrimitiveList(): ArrayList<T> {
    val list = ArrayList<T>()
    for (i in 0 until length()) {
        list.add(opt(i) as T)
    }
    return list
}