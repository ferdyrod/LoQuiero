package com.ferdyrodriguez.loquiero.base

import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.ferdyrodriguez.loquiero.R
import com.ferdyrodriguez.loquiero.navigation.Navigator
import com.ferdyrodriguez.loquiero.utils.State
import org.koin.android.ext.android.inject


abstract class BaseActivity : AppCompatActivity() {

    val navigator: Navigator by inject()

    private lateinit var progressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, IntentFilter("login_required"))
        progressDialog = Dialog(this)
    }

    private val receiver: BroadcastReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val message = intent?.getStringExtra("message")
            if (message == "login_required") {
                navigator.toLogin()
            }
        }

    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver)
        super.onDestroy()
    }

    fun renderDataState(state: State?) {
        when (state) {
            State.LOADING -> showLoading()
            State.FINISHED -> dismissLoading()
        }
    }


    private fun showLoading() {
        progressDialog.let {
            it.window.setBackgroundDrawable(ColorDrawable(0))
            it.setContentView(R.layout.loading_progress)
            it.setCancelable(false)
            if (!it.isShowing)
                it.show()
        }
    }

    private fun dismissLoading() {
        if (progressDialog.isShowing)
            progressDialog.dismiss()
    }
}