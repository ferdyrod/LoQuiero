package com.ferdyrodriguez.loquiero.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    lateinit var binding: ViewDataBinding

    private var items: MutableList<Any> = mutableListOf()

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) = holder.bind(getItem(position))

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
}