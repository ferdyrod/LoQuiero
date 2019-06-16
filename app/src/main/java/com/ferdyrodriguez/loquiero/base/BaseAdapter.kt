package com.ferdyrodriguez.loquiero.base

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.ferdyrodriguez.loquiero.extensions.inflater

abstract class BaseAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    lateinit var binding: ViewDataBinding

    private var items: MutableList<Any> = mutableListOf()

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) = holder.bind(getItem(position))

    fun inflateBinding(parent: ViewGroup, @LayoutRes layout: Int) : ViewDataBinding {
        binding = DataBindingUtil.inflate<ViewDataBinding>(parent.inflater(), layout, parent, false)
        return binding
    }

    private fun getItem(position: Int) = items[position]

    fun addItems(list: List<Any>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun clearAll(){
        if(items.isNotEmpty()) {
            items.clear()
            notifyDataSetChanged()
        }
    }

    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }
}