package com.ferdyrodriguez.loquiero.usecases.search

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.ferdyrodriguez.loquiero.BR
import com.ferdyrodriguez.loquiero.R
import com.ferdyrodriguez.loquiero.base.BaseAdapter
import com.ferdyrodriguez.loquiero.base.BaseViewHolder
import com.ferdyrodriguez.loquiero.models.ProductItem

class SearchProductAdapter(private val viewModel: SearchViewModel) : BaseAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        inflateBinding(parent, R.layout.item_product)
        return SearchProductHolder(binding)
    }

    inner class SearchProductHolder(binding: ViewDataBinding) : BaseViewHolder(binding) {
        override fun bind(item: Any) {
            val product = item as ProductItem
            binding.setVariable(BR.item, product)
            binding.setVariable(BR.viewModel, viewModel)
            binding.executePendingBindings()
        }
    }
}