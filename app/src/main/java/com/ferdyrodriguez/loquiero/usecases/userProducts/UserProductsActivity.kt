package com.ferdyrodriguez.loquiero.usecases.userProducts

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.ferdyrodriguez.loquiero.R
import com.ferdyrodriguez.loquiero.databinding.ActivityUserProductsBinding
import com.ferdyrodriguez.loquiero.extensions.toast
import com.ferdyrodriguez.loquiero.models.ProductItem
import com.ferdyrodriguez.loquiero.utils.Event
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class UserProductsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserProductsBinding

    private val viewModel: UserProductViewModel by inject()
    private val adapter: UserProductsAdapter by inject { parametersOf(viewModel) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_products)
        binding.lifecycleOwner = this

        setSupportActionBar(binding.baseToolbar.toolbar)
        supportActionBar.let {
            binding.baseToolbar.AppBarTitle.text = getString(R.string.user_products)
            it?.setHomeButtonEnabled(true)
            it?.setDisplayHomeAsUpEnabled(true)
            it?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        val layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        viewModel.products.observe(this, Observer(::setList))
        viewModel.openMenu.observe(this, Observer(::openMenu))
        viewModel.deletedSuccessfully.observe(this, Observer(::deletedProduct))
    }

    override fun onResume() {
        super.onResume()
        viewModel.getProducts()
        registerForContextMenu(binding.recyclerView)
    }

    override fun onPause() {
        super.onPause()
        unregisterForContextMenu(binding.recyclerView)
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

    private fun setList(products: List<ProductItem>) {
        adapter.addItems(products)
    }

    private fun openMenu(event: Event<Pair<Int, Int>>) {
        event.getContentIfNotHandled()?.let {
            val dialog = AlertDialog.Builder(this)
                .setTitle(getString(R.string.confirm_delete_item_title))
                .setMessage(getString(R.string.confirm_delete_item_message))
                .setNegativeButton(getString(R.string.cancel)) { _, _ -> }
                .setPositiveButton(getString(R.string.accept)) { _, _ ->
                    adapter.removeItem(it.second)
                    viewModel.deleteProduct(it.first)
                }
                .create()
            dialog.show()
        }
    }

    private fun deletedProduct(event: Event<Boolean>) {
        event.getContentIfNotHandled()?.let {
            if (it)
                toast(getString(R.string.product_deleted))
        }
    }
}
