package com.ferdyrodriguez.loquiero.usecases.productDetail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ferdyrodriguez.loquiero.R
import com.ferdyrodriguez.loquiero.databinding.ActivityProductDetailBinding
import com.ferdyrodriguez.loquiero.models.ProductItem
import com.ferdyrodriguez.loquiero.utils.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductDetailActivity : AppCompatActivity() {

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
    }

    override fun onOptionsItemSelected(item: MenuItem?) =
        when(item?.itemId) {
            android.R.id.home -> { onBackPressed(); true }
            else -> { super.onOptionsItemSelected(item) }
        }

}
