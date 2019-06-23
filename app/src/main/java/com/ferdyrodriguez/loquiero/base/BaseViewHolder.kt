package com.ferdyrodriguez.loquiero.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


abstract class BaseViewHolder(var binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    abstract fun bind(item: Any)
}