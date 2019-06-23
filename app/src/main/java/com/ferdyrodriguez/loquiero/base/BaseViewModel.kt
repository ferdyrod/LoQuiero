package com.ferdyrodriguez.loquiero.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ferdyrodriguez.domain.exceptions.Failure


abstract class BaseViewModel: ViewModel() {

    var _failure: MutableLiveData<Failure> = MutableLiveData()
    val failure: LiveData<Failure>
        get() = _failure


    protected fun handleFailure(failure: Failure) {
        _failure.value = failure
    }
}