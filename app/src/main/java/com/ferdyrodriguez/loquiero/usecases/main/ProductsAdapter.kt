package com.ferdyrodriguez.loquiero.usecases.main

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.ferdyrodriguez.loquiero.BR
import com.ferdyrodriguez.loquiero.R
import com.ferdyrodriguez.loquiero.base.BaseAdapter
import com.ferdyrodriguez.loquiero.base.BaseViewHolder
import com.ferdyrodriguez.loquiero.models.ProductItem

class ProductsAdapter(private val viewModel: MainViewModel): BaseAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        inflateBinding(parent, R.layout.item_product)
        return ProductsHolder(binding)
    }

    inner class ProductsHolder(binding: ViewDataBinding): BaseViewHolder(binding){
        override fun bind(item: Any) {
            val product: ProductItem = item as ProductItem
            binding.setVariable(BR.item, product)
            binding.setVariable(BR.viewModel, viewModel)
            binding.executePendingBindings()
        }
    }
}