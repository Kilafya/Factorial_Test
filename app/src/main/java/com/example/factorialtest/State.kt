package com.example.factorialtest

class State(
    val isError: Boolean = false,
    val isInProgress: Boolean = false,
    val factorial: String = EMPTY_STRING
) {
    companion object {

        private const val EMPTY_STRING = ""
    }
}