package com.ferdyrodriguez.loquiero.usecases.main


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ferdyrodriguez.loquiero.base.BaseViewModel
import com.ferdyrodriguez.loquiero.utils.Event

class MainViewModel() : BaseViewModel() {

    private var _navigationToAdd = MutableLiveData<Event<Boolean>>()
    val navigateToAdd : LiveData<Event<Boolean>>
        get() = _navigationToAdd

    fun goToAddProduct(){
        _navigationToAdd.value = Event(true)
    }
}