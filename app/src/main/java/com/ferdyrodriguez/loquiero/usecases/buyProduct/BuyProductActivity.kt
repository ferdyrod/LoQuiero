package com.ferdyrodriguez.loquiero.usecases.buyProduct

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.ferdyrodriguez.loquiero.R
import com.ferdyrodriguez.loquiero.base.BaseActivity
import com.ferdyrodriguez.loquiero.databinding.ActivityBuyProductBinding
import com.ferdyrodriguez.loquiero.extensions.toast
import com.ferdyrodriguez.loquiero.utils.Constants
import com.ferdyrodriguez.loquiero.utils.Event
import org.koin.android.ext.android.inject

class BuyProductActivity : BaseActivity() {

    private lateinit var binding: ActivityBuyProductBinding

    private val viewModel: BuyProductViewModel by inject()
    private var productId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_buy_product)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        productId = intent?.extras?.getInt(Constants.PRODUCT_ID, -1)

        setSupportActionBar(binding.baseToolbar.toolbar)
        supportActionBar?.let {
            binding.baseToolbar.AppBarTitle.text = getString(R.string.payment_method)
            it.setHomeButtonEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        viewModel.validateCard.observe(this, Observer(::validateCard))
        viewModel.productBought.observe(this, Observer(::handleNavigation))

        viewModel.state.observe(this, Observer(this::renderDataState))
    }

    private fun validateCard(event: Event<Boolean>?) {
        event?.getContentIfNotHandled()?.let {
            if(it)
                getCardToken()
        }
    }

    private fun handleNavigation(event: Event<Boolean>){
        event.getContentIfNotHandled()?.let {
            if(it) {
                toast("Enhorabuena, has comprado el producto")
                setResult(RESULT_OK)
                finish()
            } else {
                setResult(RESULT_CANCELED)
            }
        }
    }

    private fun getCardToken() {
        val cardToTokenize = binding.stripeView.card
        if(cardToTokenize != null)
            viewModel.createToken(productId!!, cardToTokenize)
        else
            toast("Datos de la tarjeta no son válidos")
    }

    override fun onOptionsItemSelected(item: MenuItem?) =
        when(item?.itemId) {
            android.R.id.home -> { onBackPressed(); true }
            else -> { super.onOptionsItemSelected(item) }
        }

}
