package com.ferdyrodriguez.loquiero.usecases.userProfile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.loquiero.R
import com.ferdyrodriguez.loquiero.base.BaseActivity
import com.ferdyrodriguez.loquiero.databinding.ActivityUserProfileBinding
import com.ferdyrodriguez.loquiero.extensions.toast
import com.ferdyrodriguez.loquiero.utils.Event
import id.zelory.compressor.Compressor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import pl.aprilapps.easyphotopicker.MediaFile
import pl.aprilapps.easyphotopicker.MediaSource

class UserProfileActivity : BaseActivity() {

    companion object{
        const val PERMISSION_REQUEST = 2101
    }

    private lateinit var binding: ActivityUserProfileBinding

    private val viewModel: UserProfileViewModel by inject()
    private val easyImage: EasyImage by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_profile)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setSupportActionBar(binding.baseToolbar.toolbar)
        supportActionBar.let {
            it?.setHomeButtonEnabled(true)
            it?.setDisplayHomeAsUpEnabled(true)
            it?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        viewModel.postalCode.observe(this, Observer(::checkPostalCode))

        viewModel.openChooser.observe(this, Observer(::openChooser))
        viewModel.isProfileSaved.observe(this, Observer(::handleProfileSaved))
        viewModel.failure.observe(this, Observer(::handleFailure))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        easyImage.handleActivityResult(requestCode, resultCode, data, this, object: DefaultCallback(){
            override fun onMediaFilesPicked(imageFiles: Array<MediaFile>, source: MediaSource) {
                if(imageFiles.isNotEmpty()) {
                    GlobalScope.async {
                        val file = withContext(Dispatchers.Main) {
                            Compressor(this@UserProfileActivity).compressToFile(imageFiles[0].file)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.editMode -> {
                viewModel.setEditMode(true)
                true
            }
            R.id.userProduct -> {
                navigator.toUserProducts()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openChooser(event: Event<Boolean>?) {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            easyImage.openChooser(this)
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),
                PERMISSION_REQUEST
            )
        }
    }

    private fun checkPostalCode(postalCode: String?){
        viewModel.cleanPostalCode(postalCode)
    }

    private fun handleProfileSaved(event: Event<Boolean>?) {
        event?.getContentIfNotHandled()?.let {
            if(it) {
                toast(getString(R.string.profile_saved))
                setResult(RESULT_OK)
            } else
                setResult(RESULT_CANCELED)
        }
    }

    private fun handleFailure(failure: Failure) {
        if (!failure.errorMessage.isNullOrEmpty())
            toast(failure.errorMessage.toString())
        else
            toast(getString(R.string.problem_try_again))
    }
}
