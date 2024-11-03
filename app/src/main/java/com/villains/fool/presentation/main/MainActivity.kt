package com.villains.fool.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.villains.fool.R
import com.villains.fool.databinding.ActivityMainBinding
import com.villains.fool.presentation.main.home.Home
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainFragmentContainer, Home()).commit()
    }
}