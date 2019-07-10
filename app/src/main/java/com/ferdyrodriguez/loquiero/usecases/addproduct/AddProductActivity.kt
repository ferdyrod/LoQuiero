package com.ferdyrodriguez.loquiero.usecases.addproduct

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.loquiero.R
import com.ferdyrodriguez.loquiero.base.BaseActivity
import com.ferdyrodriguez.loquiero.databinding.ActivityAddProductBinding
import com.ferdyrodriguez.loquiero.extensions.toast
import com.ferdyrodriguez.loquiero.utils.Event
import id.zelory.compressor.Compressor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import pl.aprilapps.easyphotopicker.MediaFile
import pl.aprilapps.easyphotopicker.MediaSource

class AddProductActivity : BaseActivity() {

    companion object {
        const val PERMISSION_REQUEST = 1101
    }

    private lateinit var binding: ActivityAddProductBinding

    private val viewModel: AddProductViewModel by viewModel()
    private val easyImage: EasyImage by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_product)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setSupportActionBar(binding.baseToolbar.toolbar)
        supportActionBar.let {
            binding.baseToolbar.AppBarTitle.text = getString(R.string.add_product)
            it?.setHomeButtonEnabled(true)
            it?.setDisplayHomeAsUpEnabled(true)
            it?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }



        viewModel.title.observe(this, Observer(::checkTitle))
        viewModel.price.observe(this, Observer(::checkPrice))

        viewModel.openChooser.observe(this, Observer(::openChooser))
        viewModel.isProductAdded.observe(this, Observer(::handleNavigation))
        viewModel.failure.observe(this, Observer(::handleFailure))
        viewModel.state.observe(this, Observer(this::renderDataState))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        easyImage.handleActivityResult(requestCode, resultCode, data, this, object: DefaultCallback(){
            override fun onMediaFilesPicked(imageFiles: Array<MediaFile>, source: MediaSource) {
                if(imageFiles.isNotEmpty()) {
                    GlobalScope.async {
                        val file = withContext(Dispatchers.Main) {
                            Compressor(this@AddProductActivity).compressToFile(imageFiles[0].file)
                        }
                        viewModel.setMediaFile(file)
                    }
                }
            }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                easyImage.openChooser(this)
            } else {
                toast("Se necesita permiso para usar la camera del dispositivo")
            }
        }
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

    private fun openChooser(event: Event<Boolean>?) {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            easyImage.openChooser(this)
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), PERMISSION_REQUEST)
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
