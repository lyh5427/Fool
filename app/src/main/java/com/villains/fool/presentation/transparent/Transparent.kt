package com.villains.fool.presentation.transparent

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.villains.fool.R
import com.villains.fool.databinding.ActivityTransparentBinding
import com.villains.fool.presentation.Const
import com.villains.fool.presentation.transparent.fragment.SelectCountry
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Transparent : AppCompatActivity() {

    lateinit var binding: ActivityTransparentBinding

    var type: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityTransparentBinding.inflate(layoutInflater)
        type = intent.getStringExtra(Const.SELECTOR_TYPE).toString()

        var frag = when (type) {
            Const.SELECTOR_TYPE_FIRST -> {
                SelectCountry.newInstance(
                    getString(R.string.ctr_selector_title),
                    getString(R.string.close),
                    Const.SELECTOR_TYPE_FIRST)
            }

            else -> {
                SelectCountry.newInstance(
                    getString(R.string.ctr_selector_title2),
                    getString(R.string.go_back),
                    Const.SELECTOR_TYPE_SECOND)
            }
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.transparentFragmentContainer, frag).commit()

        setContentView(binding.root)
    }
}