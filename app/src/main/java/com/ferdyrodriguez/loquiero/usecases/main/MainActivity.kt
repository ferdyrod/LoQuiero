package com.ferdyrodriguez.loquiero.usecases.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.ferdyrodriguez.loquiero.R
import com.ferdyrodriguez.loquiero.base.BaseActivity
import com.ferdyrodriguez.loquiero.databinding.ActivityMainBinding
import com.ferdyrodriguez.loquiero.extensions.toast
import com.ferdyrodriguez.loquiero.models.ProductItem
import com.ferdyrodriguez.loquiero.utils.Event
import kotlinx.android.synthetic.main.toolbar.view.*
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity() {

    companion object {
        const val ADD_REQUEST_CODE = 1001
    }

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by inject()
    private val adapter: ProductsAdapter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setSupportActionBar(binding.baseToolbar.toolbar)
        supportActionBar.let {
            title = getString(R.string.app_name)
        }

        val layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        viewModel.navigateToAdd.observe(this, Observer(this::navigateToAddProduct))
        viewModel.products.observe(this, Observer(::setList))
    }

    override fun onResume() {
        super.onResume()
        viewModel.getProducts()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == ADD_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            toast("debo de llamar al endpoint para traer todos los productos")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            R.id.user_products -> { navigator.toUserProducts(); true}
            else -> { return super.onOptionsItemSelected(item) }
        }

    }

    private fun navigateToAddProduct(event: Event<Boolean>){
        event.getContentIfNotHandled()?.let {
            navigator.toAddProduct(this, ADD_REQUEST_CODE )
        }
    }

    private fun setList(products: List<ProductItem>){
        adapter.addItems(products)
    }
}
