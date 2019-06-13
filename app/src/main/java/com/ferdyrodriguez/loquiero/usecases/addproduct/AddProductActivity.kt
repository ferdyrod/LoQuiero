package com.ferdyrodriguez.loquiero.usecases.addproduct

import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.loquiero.R
import com.ferdyrodriguez.loquiero.base.BaseActivity
import com.ferdyrodriguez.loquiero.databinding.ActivityAddProductBinding
import com.ferdyrodriguez.loquiero.extensions.toast
import com.ferdyrodriguez.loquiero.utils.Event
import kotlinx.android.synthetic.main.toolbar.view.*
import org.koin.android.ext.android.inject

class AddProductActivity : BaseActivity() {

    private lateinit var binding: ActivityAddProductBinding

    private val viewModel: AddProductViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_product)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setSupportActionBar(binding.baseToolbar.toolbar)
        supportActionBar.let {
            title = getString(R.string.add_product)
            it?.setHomeButtonEnabled(true)
            it?.setDisplayHomeAsUpEnabled(true)
            it?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        viewModel.title.observe(this, Observer(::checkTitle))
        viewModel.price.observe(this, Observer(::checkPrice))

        viewModel.isProductAdded.observe(this, Observer(::handleNavigation))
        viewModel.failure.observe(this, Observer(::handleFailure))
    }

    private fun handleFailure(failure: Failure) {
        if (!failure.errorMessage.isNullOrEmpty())
            toast(failure.errorMessage.toString())
        else
            toast(getString(R.string.problem_try_again))
    }

    private fun handleNavigation(isProductAdded: Event<Boolean>) {
        isProductAdded.peekContent()?.let {
            if(it) {
                toast(getString(R.string.product_added))
                setResult(RESULT_OK)
            } else {
                toast(getString(R.string.problem_try_again))
                setResult(RESULT_CANCELED)
            }
            finish()
        }
    }

    private fun checkPrice(price: String) {
        viewModel.validatePrice(price)
    }

    private fun checkTitle(title: String) {
        viewModel.validateTitle(title)
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
}
