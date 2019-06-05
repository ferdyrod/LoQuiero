package com.ferdyrodriguez.loquiero

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ferdyrodriguez.domain.usecases.RegisterUserUseCase
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val useCase: RegisterUserUseCase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        useCase(RegisterUserUseCase.Params("","")) { it ->
            it.either({Log.d("test", "left")},{ Log.d("test", "right")})
        }
    }
}
