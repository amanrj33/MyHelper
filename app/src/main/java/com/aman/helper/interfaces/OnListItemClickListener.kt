package com.aman.helper.interfaces

interface OnListItemClickListener<DataModel> {
    fun onListDataItemClicked(position:Int, item: DataModel)

    fun onListAddItemCLicked()
}