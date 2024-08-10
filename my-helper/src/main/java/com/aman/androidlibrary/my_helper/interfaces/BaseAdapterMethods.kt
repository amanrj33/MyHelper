package com.aman.androidlibrary.my_helper.interfaces

interface BaseAdapterMethods<T> {
    fun replaceList(newList: ArrayList<T>)

    fun append(item: T, atBeginning: Boolean = false, addUnique: Boolean)

    fun update(item: T)

    fun remove(item: T)

    fun removeAt(position: Int)

    fun clear()
}