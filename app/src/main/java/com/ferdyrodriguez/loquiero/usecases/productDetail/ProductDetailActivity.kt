package com.ferdyrodriguez.loquiero.usecases.productDetail

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.ferdyrodriguez.loquiero.R
import com.ferdyrodriguez.loquiero.base.BaseActivity
import com.ferdyrodriguez.loquiero.databinding.ActivityProductDetailBinding
import com.ferdyrodriguez.loquiero.models.ProductItem
import com.ferdyrodriguez.loquiero.utils.Constants
import com.ferdyrodriguez.loquiero.utils.Event
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductDetailActivity : BaseActivity() {

    companion object {
        const val BUY_PRODUCT = 3001
    }

    private lateinit var binding: ActivityProductDetailBinding

    private val viewModel: ProductDetailViewModel by viewModel { parametersOf(product) }
    private var product: ProductItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)
        product = intent?.extras?.getParcelable(Constants.PRODUCT)

        binding.product = product
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        setSupportActionBar(binding.baseToolbar.toolbar)
        supportActionBar?.let {
            it.setHomeButtonEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        viewModel.navigateToBuy.observe(this, Observer(::handleNavigationToBuy))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == BUY_PRODUCT && resultCode == RESULT_OK) {
            finish()
        }
    }

    fun handleNavigationToBuy(event: Event<Int>) {
        event.getContentIfNotHandled()?.let {
            navigator.toBuyProduct(this@ProductDetailActivity, BUY_PRODUCT, it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?) =
        when(item?.itemId) {
            android.R.id.home -> { onBackPressed(); true }
            else -> { super.onOptionsItemSelected(item) }
        }

}
