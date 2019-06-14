package com.ferdyrodriguez.loquiero.usecases.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.ferdyrodriguez.loquiero.R
import com.ferdyrodriguez.loquiero.base.BaseActivity
import com.ferdyrodriguez.loquiero.databinding.ActivityMainBinding
import com.ferdyrodriguez.loquiero.extensions.toast
import com.ferdyrodriguez.loquiero.utils.Event
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity() {

    companion object {
        const val ADD_REQUEST_CODE = 1001
    }

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.navigateToAdd.observe(this, Observer(this::navigateToAddProduct))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == ADD_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            toast("debo de llamar al endpoint para traer todos los productos")
        }
    }

    private fun navigateToAddProduct(event: Event<Boolean>){
        event.getContentIfNotHandled()?.let {
            navigator.toAddProduct(this, ADD_REQUEST_CODE )
        }
    }
}
