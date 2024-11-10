package com.villains.fool.presentation.main.home.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.hardware.input.InputManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.villains.fool.Application
import com.villains.fool.R
import com.villains.fool.Util
import com.villains.fool.databinding.CalculatorBinding
import com.villains.fool.databinding.FragmentExchangeBinding
import com.villains.fool.presentation.Const
import com.villains.fool.presentation.transparent.Transparent
import com.villains.fool.singleClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.E

@AndroidEntryPoint
class ExchangeFragment : Fragment() {

    private lateinit var binding: FragmentExchangeBinding

    private lateinit var firstSelector: ActivityResultLauncher<Intent>
    private lateinit var secondSelector: ActivityResultLauncher<Intent>

    private var hasFocus = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentExchangeBinding.inflate(inflater, container, false)

        binding.exchangeSubTitle.setOnClickListener {
            startActivity(Intent(requireContext(), Transparent::class.java))
        }

        setListener()
        setResultLauncher()

        setBaseCountryView()
        setChangeCountryView()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun setResultLauncher() {
        firstSelector = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            setBaseCountryView()

            startActivity(
                Intent(requireContext(), Transparent::class.java)
                    .apply {
                        putExtra(Const.SELECTOR_TYPE, Const.SELECTOR_TYPE_SECOND)
                    })
        }

        secondSelector = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            setChangeCountryView()

            binding.calculator.root.visibility = View.VISIBLE

            hasFocus = true
        }
    }

    private fun setListener() = with(binding) {
        layoutBaseCtr.singleClickListener { startSelector() }

        layoutChangeCtr.singleClickListener { startSelector() }


        amountEt.singleClickListener {
            calculator.root.visibility = if (!hasFocus) {
                hasFocus = !hasFocus
                View.VISIBLE
            } else {
                hasFocus = !hasFocus
                View.GONE
            }
        }

        calculator.num1.singleClickListener { setInputText("1") }
        calculator.num2.singleClickListener { setInputText("2") }
        calculator.num3.singleClickListener { setInputText("3") }
        calculator.num4.singleClickListener { setInputText("4") }
        calculator.num5.singleClickListener { setInputText("5") }
        calculator.num6.singleClickListener { setInputText("6") }
        calculator.num7.singleClickListener { setInputText("7") }
        calculator.num8.singleClickListener { setInputText("8") }
        calculator.num9.singleClickListener { setInputText("9") }
        calculator.num0.singleClickListener { setInputText("0") }
        calculator.num00.singleClickListener { setInputText("00") }
        calculator.dot.singleClickListener { setInputText(".") }
        calculator.plus.singleClickListener { setInputText("+") }
        calculator.minus.singleClickListener { setInputText("-") }
        calculator.ac.singleClickListener { deleteText(true) }
        calculator.delete.singleClickListener { deleteText(false) }

        amountEt.inputType = 0

        val manager = requireContext()
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        manager.showSoftInput(amountEt, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun startSelector() {
        if (Application.prefs.baseCountry == "" ) {
            firstSelector.launch(Intent(requireContext(), Transparent::class.java)
                .apply {
                    putExtra(Const.SELECTOR_TYPE, Const.SELECTOR_TYPE_FIRST)
                })
        } else if (Application.prefs.exchangeCountry == "") {
            firstSelector.launch(Intent(requireContext(), Transparent::class.java)
                .apply {
                    putExtra(Const.SELECTOR_TYPE, Const.SELECTOR_TYPE_SECOND)
                })
        } else {
            firstSelector.launch(Intent(requireContext(), Transparent::class.java)
                .apply {
                    putExtra(Const.SELECTOR_TYPE, Const.SELECTOR_TYPE_FIRST)
                })
        }


    }

    private fun setInputText(text: String) = with(binding) {
        val editText = amountEt.text.toString()
        if (editText.isNotEmpty() && (text == "+"|| text == "-")) {
            if (editText.last().toString() == "+" || editText.last().toString() == "-") return@with
        }
        amountEt.text = Editable.Factory.getInstance().newEditable("${amountEt.text}${text}")

        if (text == "+" || text == "-") return

        resultSum.text = Util.calculate(amountEt.text.toString())

    }

    private fun deleteText(isAll: Boolean) = with(binding) {
        var text = amountEt.text

        Log.d(Application.TAG, "${text.last()}")

        if (isAll) {
            text = text.delete(0, text.length)
        } else {
            text = text.delete(text.length-1, text.length)
        }
        if (text.isNotEmpty()) {
            if (text.last().toString() == "+" || text.last().toString() == "-") {
                text = text.delete(text.length-1, text.length)
            }
        }


        amountEt.text = Editable.Factory.getInstance().newEditable(text)
        resultSum.text = Util.calculate(text.toString())


    }

    private fun setBaseCountryView() = with(binding) {
        val countryName = Application.prefs.baseCountry

        baseCtrImg.setImageDrawable(
            Util.countryImage(countryName, resources))

        baseCtrName.text = Util.countryName(requireContext(), countryName)
        resultSumDes.text = Util.unit(countryName, resources)
        baseUnit.text = Util.unitString(countryName, resources)
    }

    private fun setChangeCountryView() = with(binding) {
        val countryName = Application.prefs.exchangeCountry


        changeCtrImg.setImageDrawable(
            Util.countryImage(countryName, resources))

        changeCtrName.text = Util.countryName(requireContext(), countryName)
        resultCalDes.text = Util.unit(countryName, resources)
        exchangeUnit.text = Util.unitString(countryName, resources)

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ExchangeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}