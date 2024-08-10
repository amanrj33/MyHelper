package com.aman.helper.view.adapters.recycler

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class BaseViewHolder<T : ViewBinding>(var binding: T) : RecyclerView.ViewHolder(binding.root)
