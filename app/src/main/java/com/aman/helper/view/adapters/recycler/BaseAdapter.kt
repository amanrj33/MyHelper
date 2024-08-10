package com.aman.helper.view.adapters.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.aman.helper.interfaces.BaseAdapterMethods
import com.aman.helper.interfaces.OnListItemClickListener

abstract class BaseAdapter<Model, Binding : ViewBinding>(
    val context: Context,
    val data: ArrayList<Model>,
    val listener: OnListItemClickListener<Model>?
) : RecyclerView.Adapter<BaseViewHolder<Binding>?>(), BaseAdapterMethods<Model> {

    abstract fun createBinding(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): Binding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Binding> {
        return BaseViewHolder(createBinding(viewType, LayoutInflater.from(context), parent))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Binding>, position: Int) {

        data[position].let {
            holder.binding.root.setOnClickListener { _ ->
                listener?.onListDataItemClicked(position, it)
            }
            prepareView(holder, position, it)
        }
    }

    abstract fun prepareView(
        holder: BaseViewHolder<Binding>,
        position: Int,
        model: Model
    )

    val itemCount: Int
        get() = data.size

    override fun replaceList(newList: ArrayList<Model>) {
        data.clear()
        data.addAll(newList)
        notifyDataSetChanged()
    }

    override fun append(item: Model, atBeginning: Boolean, addUnique: Boolean) {
        if (addUnique && data.contains(item))
            return

        data.add(item)
        notifyItemInserted(data.size - 1)
    }

    override fun update(model: Model) {
        data.indexOfFirst { it == model }.takeIf { it > -1 }?.let {
            data[it] = model
            notifyItemChanged(it)
        }
    }

    override fun remove(item: Model) {
        removeAt(data.indexOf(item))
    }

    override fun removeAt(position: Int) {
        if (position < 0 || position >= data.size)
            return
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun clear() {
        val size = data.size
        data.clear()
        notifyItemRangeRemoved(0, size)
    }
}
