package com.aman.androidlibrary.my_helper.support.extensions

import com.aman.androidlibrary.my_helper.models.BaseModel
import org.json.JSONArray

fun <T> ArrayList<T>.has(index: Int): Boolean {
    return isNotEmpty() && size > index
}

fun <T> ArrayList<T>.lastIndex(): Int {
    return size - 1
}

fun <T> ArrayList<T>.update(predicate: (T) -> Boolean, item: T, upsert: Boolean = true): Int {
    var itemIndex = indexOfFirst(predicate)
    if (itemIndex != -1) {
        set(itemIndex, item)
    } else if (upsert) {
        add(item)
        itemIndex = lastIndex()
    }
    return itemIndex
}

fun <T : BaseModel> ArrayList<T>.update(item: T, upsert: Boolean = true): Int {
    return update({ it.id == item.id }, item, upsert)
}

fun <T : BaseModel> ArrayList<T>.getIndexFromId(id: String?, fallbackToFirst: Boolean = true): Int {
    var itemIndex = indexOfFirst { it.id == id }
    if (itemIndex == -1 && fallbackToFirst && isNotEmpty()) {
        itemIndex = 0
    }
    return itemIndex
}

fun <T : BaseModel> ArrayList<T>.getFromId(id: String?, fallbackToFirst: Boolean = true): T? {
    if (size == 0) {
        return null
    }

    var itemIndex = indexOfFirst { it.id == id }
    if (itemIndex == -1 && fallbackToFirst && isNotEmpty()) {
        itemIndex = 0
    }
    return if (itemIndex != -1) get(itemIndex) else null
}

fun <T : BaseModel> ArrayList<T>.toJson(): JSONArray {
    val jsonArray = JSONArray()
    forEach {
        jsonArray.put(it.toJson())
    }
    return jsonArray
}