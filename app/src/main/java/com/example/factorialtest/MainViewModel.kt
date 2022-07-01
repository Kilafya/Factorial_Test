package com.example.factorialtest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import java.math.BigInteger
import kotlin.concurrent.thread
import kotlin.coroutines.suspendCoroutine

class MainViewModel: ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    private val coroutineScope = CoroutineScope(Dispatchers.Main + CoroutineName("my coroutine scope"))

    fun calculate(value: String?) {
        if (value.isNullOrBlank()) {
            _state.value = Error
            return
        }
        _state.value = Progress
        coroutineScope.launch {
            val number = value.toLong()
            val result = withContext(Dispatchers.Default) {
                factorial(number)
            }
            _state.value = Factorial(result)
        }
    }

//    fun calculate(value: String?) {
//        if (value.isNullOrBlank()) {
//            _state.value = Error
//            return
//        }
//        _state.value = Progress
//        viewModelScope.launch {
//            val number = value.toLong()
//            withContext(Dispatchers.Default) {
//                val result = factorial(number)
//                withContext(Dispatchers.Main) {
//                    _state.value = Factorial(result)
//                }
//            }
//        }
//    }

    private fun factorial(number: Long): String {
        var result = BigInteger.ONE
        for (i in 1..number) {
            result = result.multiply(BigInteger.valueOf(i))
        }
        return result.toString()
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }

//    private suspend fun factorial(number: Long): String {
//        return withContext(Dispatchers.Default) {
//            var result = BigInteger.ONE
//            for (i in 1..number) {
//                result = result.multiply(BigInteger.valueOf(i))
//            }
//            result.toString()
//        }
//    }

//    private suspend fun factorial(number: Long): String {
//        return suspendCoroutine {
//            thread {
//                var result = BigInteger.ONE
//                for (i in 1..number) {
//                    result = result.multiply(BigInteger.valueOf(i))
//                }
//                it.resumeWith(Result.success(result.toString()))
//            }
//        }
//    }
}