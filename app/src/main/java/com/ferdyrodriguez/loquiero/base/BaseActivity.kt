package com.ferdyrodriguez.loquiero.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ferdyrodriguez.loquiero.navigation.Navigator
import org.koin.android.ext.android.inject


abstract class BaseActivity : AppCompatActivity() {

    val navigator: Navigator by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}