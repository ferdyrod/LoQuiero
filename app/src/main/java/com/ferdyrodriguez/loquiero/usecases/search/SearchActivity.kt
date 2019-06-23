package com.ferdyrodriguez.loquiero.usecases.search

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.ferdyrodriguez.loquiero.R
import com.ferdyrodriguez.loquiero.base.BaseActivity
import com.ferdyrodriguez.loquiero.databinding.ActivitySearchBinding
import com.ferdyrodriguez.loquiero.models.ProductItem
import com.ferdyrodriguez.loquiero.utils.Event
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class SearchActivity : BaseActivity() {

    private lateinit var binding: ActivitySearchBinding

    private val viewModel: SearchViewModel by inject()
    private val adapter: SearchProductAdapter by inject { parametersOf(viewModel) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        binding.lifecycleOwner = this

        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setHomeButtonEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        val layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.getProducts(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?) = false

        })

        viewModel.products.observe(this, Observer(::setlist))
        viewModel.navigateToDetail.observe(this, Observer(::navigateToDetail))


    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun navigateToDetail(event: Event<ProductItem>) {
        event.getContentIfNotHandled()?.let {
            navigator.toProductDetail(it)
        }
    }

    private fun setlist(list: List<ProductItem>) {
        adapter.addItems(list)
    }
}
