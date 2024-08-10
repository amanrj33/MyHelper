package com.aman.base.helper.view.adapters.recycler

import android.content.Context
import androidx.viewbinding.ViewBinding
import com.aman.base.helper.interfaces.OnListItemClickListener

abstract class BaseItemSelectableAdapter<Model, Binding : ViewBinding>(
    context: Context,
    data: ArrayList<Model>,
    listener: OnListItemClickListener<Model>?
) : BaseAdapter<Model, Binding>(context, data, listener) {

    val selectedItems: ArrayList<Model> = ArrayList()

    private var isMultiSelectable = false
    private var isDeSelectable = false

    private var lastSelectedItemBinding: Binding? = null

    init {
        setItemSelectionParams(false, true)
    }

    fun onItemSelected(binding: Binding) {
    }

    fun onItemDeSelected(binding: Binding?) {
    }

    fun onItemEnabled(binding: Binding, dataItem: Model) {
        binding.root.isEnabled = true
    }

    fun onItemDisabled(binding: Binding, dataItem: Model) {
        binding.root.isEnabled = false
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Binding>, position: Int) {
        val dataItem = data[position]

        if (selectedItems.contains(dataItem)) onItemSelected(holder.binding)
        else onItemDeSelected(holder.binding)

        holder.binding.root.setOnClickListener {
            if (selectedItems.contains(dataItem)) {
                if (isDeSelectable) {
                    selectedItems.remove(dataItem)
                    onItemDeSelected(holder.binding)
                }
            } else {
                if (!isMultiSelectable) {
                    clearSelections()
                    lastSelectedItemBinding = holder.binding
                }
                selectedItems.add(dataItem)
                onItemSelected(holder.binding)
            }
            listener?.onListDataItemClicked(position, dataItem)
        }
    }

    fun clearSelections() {
        selectedItems.clear()
        onItemDeSelected(lastSelectedItemBinding)
        lastSelectedItemBinding = null
    }

    fun setItemSelectionParams(isMultiSelectable: Boolean, isDeSelectable: Boolean) {
        this.isMultiSelectable = isMultiSelectable
        this.isDeSelectable = isDeSelectable
    }
}
