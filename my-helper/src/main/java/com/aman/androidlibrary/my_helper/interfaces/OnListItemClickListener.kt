package com.aman.androidlibrary.my_helper.interfaces

interface OnListItemClickListener<DataModel> {
    fun onListDataItemClicked(position:Int, item: DataModel)

    fun onListAddItemCLicked()
}