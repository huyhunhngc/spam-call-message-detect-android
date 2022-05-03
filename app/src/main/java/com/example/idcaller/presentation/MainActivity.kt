package com.example.idcaller.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.idcaller.core.base.viewBindings
import com.example.idcaller.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val binding by viewBindings {
        ActivityMainBinding.inflate(it)
    }
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            setContentView(root)
            lifecycleOwner = this@MainActivity
            viewModel = this@MainActivity.viewModel
        }
    }
}