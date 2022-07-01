package com.example.factorialtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.factorialtest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeViewModel()
        setOnClickListener()
    }

    private fun setOnClickListener() {
        with(binding) {
            buttonCalculate.setOnClickListener {
                viewModel.calculate(editTextNumber.text.toString())
            }
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe(this) {
            if (it.isError) {
                Toast.makeText(this,
                    "Error: you did not entered value",
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.resetError()
            }
            if (it.isInProgress) {
                binding.progressBarLoading.visibility = View.VISIBLE
                binding.buttonCalculate.isEnabled = false
            } else {
                binding.progressBarLoading.visibility = View.GONE
                binding.buttonCalculate.isEnabled = true
            }
            if (it.factorial.isNotBlank()) {
                binding.textViewFactorial.text = it.factorial
            }
        }
    }
}