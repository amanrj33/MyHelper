package com.aman.androidlibrary.my_helper.support.rx.livedata

import androidx.lifecycle.MutableLiveData
import com.aman.androidlibrary.my_helper.support.extensions.lastIndex

open class ListLiveData<T>(): MutableLiveData<ArrayList<T>>() {
    open val list = ArrayList<T>()
    
    companion object {
        fun <T> create(newList: List<T>): ListLiveData<T> {
            val liveData = ListLiveData<T>()
            liveData.replace(newList)
            return liveData
        }
    }

    override fun setValue(value: ArrayList<T>?) {
//        super.setValue(value)
        replace(value ?: emptyList())
    }

    fun replace(newList: List<T>) {
        list.clear()
        list.addAll(newList)
        super.setValue(list)
    }

    fun add(item: T): Int {
        list.add(item)
        super.setValue(list)
        return list.lastIndex()
    }

    fun remove(item: T) {
        list.remove(item)
        super.setValue(list)
    }

    fun update(item: T, upsert: Boolean = false): Int {
        val index = list.indexOf(item)
        if (index != -1) {
            list[index] = item
            super.setValue(list)
        } else if (upsert) {
            return add(item)
        }
        return index
    }

    fun clear() {
        list.clear()
        super.setValue(list)
    }

    fun size(): Int {
        return list.size
    }

    fun get(index: Int): T? {
        if (!hasIndex(index))
            return null
        return list[index]
    }

    fun indexOf(item: T): Int {
        return list.indexOf(item)
    }

    fun contains(item: T): Boolean {
        return list.contains(item)
    }

    fun isEmpty(): Boolean {
        return list.isEmpty()
    }

    fun isNotEmpty(): Boolean {
        return list.isNotEmpty()
    }

    fun hasIndex(index: Int) = isNotEmpty() && size() > index

    fun forEach(action: (T) -> Unit) {
        list.forEach(action)
    }

    fun forEachIndexed(action: (Int, T) -> Unit) {
        list.forEachIndexed(action)
    }

    fun filter(predicate: (T) -> Boolean): List<T> {
        return list.filter(predicate)
    }

    fun indexOfFirst(predicate: (T) -> Boolean): Int {
        return list.indexOfFirst(predicate)
    }
}