package com.example.factorialtest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean>
        get() = _error

    private val _factorial = MutableLiveData<String>()
    val factorial: LiveData<String>
        get() = _factorial

    private val _progress = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean>
        get() = _progress

    fun calculate(value: String?) {
        if (value.isNullOrBlank()) {
            _error.value = true
            return
        }
        _progress.value = true
        viewModelScope.launch {
            val number = value.toLong()
            // calculate
            delay(1000)
            _progress.value = false
            _factorial.value = number.toString()
        }
    }

    fun resetError() {
        _error.value = false
    }

}