package com.ferdyrodriguez.loquiero.usecases.addproduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ferdyrodriguez.domain.models.Product
import com.ferdyrodriguez.domain.usecases.AddProductUseCase
import com.ferdyrodriguez.loquiero.base.BaseViewModel
import com.ferdyrodriguez.loquiero.utils.Event
import com.ferdyrodriguez.loquiero.utils.State
import org.koin.ext.isInt
import java.io.File

class AddProductViewModel(private val useCase: AddProductUseCase) : BaseViewModel() {

    var title: MutableLiveData<String> = MutableLiveData()
    var description: MutableLiveData<String> = MutableLiveData()
    var price: MutableLiveData<String> = MutableLiveData()
    var photo: MutableLiveData<File> = MutableLiveData()
    var isPhotoSelected: MutableLiveData<Boolean> = MutableLiveData()


    private val _openChooser: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val openChooser: LiveData<Event<Boolean>>
        get() = _openChooser

    private var _formIsValid: MutableLiveData<Boolean> = MutableLiveData()
    val formIsValid: LiveData<Boolean>
        get() = _formIsValid

    private val _isProductAdded: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val isProductAdded: LiveData<Event<Boolean>>
        get() = _isProductAdded

    private var validTitle: Boolean = false
    private var validPrice: Boolean = false
    private var formattedPrice: Int = -1

    init {
        _formIsValid.value = false
    }

    fun addProduct() {
        if (formattedPrice >= 0) {
            _state.value = State.LOADING
            useCase(AddProductUseCase.Params(title.value!!, description.value, formattedPrice, photo.value!!)) {
                it.either(::handleFailure, ::handleAddedProduct)
            }
        }
    }

    fun addPhoto() {
        _openChooser.value = Event(true)
    }

    private fun handleAddedProduct(product: Product) {
        _state.value = State.FINISHED
        _isProductAdded.value = Event(true)
    }

    private fun isFormValid(): MutableLiveData<Boolean> {
        _formIsValid.value = validTitle && validPrice && photo.value != null
        return _formIsValid
    }

    fun validateTitle(title: String) {
        validTitle = !title.isNullOrEmpty()
        isFormValid()
    }

    fun validatePrice(price: String) {
        val cleanedPrice = price.replace(",", "").replace(".", "")
        validPrice = cleanedPrice.isInt()
        if (validPrice)
            formattedPrice = cleanedPrice.toInt()
        isFormValid()
    }

    fun setMediaFile(file: File){
        photo.postValue(file)
        isPhotoSelected.value = true
        isFormValid()
    }

}