package com.ferdyrodriguez.loquiero.usecases.userProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ferdyrodriguez.data.local.PreferenceConstants
import com.ferdyrodriguez.data.local.PreferenceHelper
import com.ferdyrodriguez.domain.models.UserProfile
import com.ferdyrodriguez.domain.usecases.SaveUserProfileUseCase
import com.ferdyrodriguez.loquiero.base.BaseViewModel
import com.ferdyrodriguez.loquiero.utils.Event
import java.io.File

class UserProfileViewModel(private val useCase: SaveUserProfileUseCase,
                           pref: PreferenceHelper) : BaseViewModel() {

    var firstName: MutableLiveData<String> = MutableLiveData()
    var lastName: MutableLiveData<String> = MutableLiveData()
    var postalCode: MutableLiveData<String> = MutableLiveData()
    var phone: MutableLiveData<String> = MutableLiveData()
    var photo: MutableLiveData<File> = MutableLiveData()
    var email: String
    var localPhoto: String

    private var cleanedPostalCode: Int? = null

    var isPhotoSelected: MutableLiveData<Boolean> = MutableLiveData()


    private val _openChooser: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val openChooser: LiveData<Event<Boolean>>
        get() = _openChooser

    private var _isInEditMode: MutableLiveData<Boolean> = MutableLiveData()
    val isInEditMode: LiveData<Boolean>
        get() = _isInEditMode

    private val _isProfileSaved: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val isProfileSaved: LiveData<Event<Boolean>>
        get() = _isProfileSaved

    init {
        _isInEditMode.value = false
        firstName.value = pref.getPreference(PreferenceConstants.USER_FIRST_NAME, "")
        lastName.value = pref.getPreference(PreferenceConstants.USER_LAST_NAME, "")
        val code = pref.getPreference(PreferenceConstants.USER_POSTAL_CODE, -1)
        postalCode.value = code.toString()
        phone.value = pref.getPreference(PreferenceConstants.USER_PHONE, "")
        localPhoto = pref.getPreference(PreferenceConstants.USER_PHOTO, "") ?: ""
        email = pref.getPreference(PreferenceConstants.USER_EMAIL, "") ?: ""
    }

    fun saveProfile() {
        useCase(SaveUserProfileUseCase.Params(firstName.value, lastName.value, cleanedPostalCode, phone.value, photo.value)) {
            it.either(::handleFailure, ::handleSavedProfile)
        }
    }

    fun addPhoto() {
        if(_isInEditMode.value!!)
            _openChooser.value = Event(true)
    }

    private fun handleSavedProfile(user: UserProfile?) {
        _isProfileSaved.value = Event(true)
    }

    fun setEditMode(editMode: Boolean) {
        _isInEditMode.value = editMode
    }


    fun setMediaFile(file: File){
        photo.postValue(file)
        isPhotoSelected.value = true
    }

    fun cleanPostalCode(postalCode: String?) {
        if(!postalCode.isNullOrEmpty())
            cleanedPostalCode = postalCode.toInt()
    }

}