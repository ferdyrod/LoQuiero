package com.ferdyrodriguez.loquiero.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.loquiero.utils.State


abstract class BaseViewModel: ViewModel() {

    var _failure: MutableLiveData<Failure> = MutableLiveData()
    val failure: LiveData<Failure>
        get() = _failure

    var _state: MutableLiveData<State> = MutableLiveData()
    val state: LiveData<State>
        get() = _state

    protected fun handleFailure(failure: Failure) {
        _state.value = State.FINISHED
        _failure.value = failure
    }
}