package com.ferdyrodriguez.loquiero.usecases.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.ferdyrodriguez.loquiero.R
import com.ferdyrodriguez.loquiero.base.BaseActivity
import com.ferdyrodriguez.loquiero.databinding.ActivityMainBinding
import com.ferdyrodriguez.loquiero.extensions.visible
import com.ferdyrodriguez.loquiero.models.ProductItem
import com.ferdyrodriguez.loquiero.utils.Event
import kotlinx.android.synthetic.main.toolbar.view.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : BaseActivity() {

    companion object {
        const val ADD_REQUEST_CODE = 1001
        const val EDIT_PROFILE = 1002
    }

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by inject()
    private val adapter: ProductsAdapter by inject { parametersOf(viewModel) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.baseToolbar.profilePicture.visible()
        binding.baseToolbar.AppBarTitle.text = getString(R.string.app_name)

        val layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        viewModel.navigateToAdd.observe(this, Observer(::navigateToAddProduct))
        viewModel.navigateToDetail.observe(this, Observer(::navigateToDetail))
        viewModel.products.observe(this, Observer(::setList))

        binding.baseToolbar.profilePictureLayout.search_layout.visible()
        binding.baseToolbar.profilePicture.setOnClickListener {
            navigator.toUserProfile(this, EDIT_PROFILE)
        }
        binding.baseToolbar.searchLayout.setOnClickListener {
            navigator.toSearch()
        }
        viewModel.getPhoto()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getProducts()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDIT_PROFILE && resultCode == Activity.RESULT_OK) {
            viewModel.getPhoto()
        }
    }

    private fun navigateToAddProduct(event: Event<Boolean>) {
        event.getContentIfNotHandled()?.let {
            navigator.toAddProduct(this, ADD_REQUEST_CODE)
        }
    }

    private fun navigateToDetail(event: Event<ProductItem>) {
        event.getContentIfNotHandled()?.let {
            navigator.toProductDetail(it)
        }
    }

    private fun setList(products: List<ProductItem>) {
        adapter.addItems(products)
    }
}
