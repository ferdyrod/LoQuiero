package com.ferdyrodriguez.loquiero.usecases.userProducts

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.ferdyrodriguez.loquiero.BR
import com.ferdyrodriguez.loquiero.R
import com.ferdyrodriguez.loquiero.base.BaseAdapter
import com.ferdyrodriguez.loquiero.base.BaseViewHolder
import com.ferdyrodriguez.loquiero.models.ProductItem
import kotlinx.android.synthetic.main.item_product.view.*

class UserProductsAdapter(private val viewModel: UserProductViewModel): BaseAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        inflateBinding(parent, R.layout.item_product)
        return UserProductsHolder(binding)
    }

    inner class UserProductsHolder(binding: ViewDataBinding): BaseViewHolder(binding){
        override fun bind(item: Any) {
            val product: ProductItem = item as ProductItem
            binding.setVariable(BR.item, product)
            binding.setVariable(BR.viewModel, viewModel)
            binding.executePendingBindings()

            binding.root.productCard.setOnCreateContextMenuListener { contextMenu, _, _ ->
                contextMenu.add(binding.root.context.getString(R.string.delete)).setOnMenuItemClickListener {
                    viewModel.openMenuToDelete(item.id, adapterPosition)
                    true
                }
            }
        }
    }
}