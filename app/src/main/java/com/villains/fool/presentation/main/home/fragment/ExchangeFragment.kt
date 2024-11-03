package com.villains.fool.presentation.main.home.fragment

import android.content.Context
import android.content.Intent
import android.hardware.input.InputManager
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import com.villains.fool.R
import com.villains.fool.databinding.CalculatorBinding
import com.villains.fool.databinding.FragmentExchangeBinding
import com.villains.fool.presentation.Const
import com.villains.fool.presentation.transparent.Transparent
import com.villains.fool.singleClickListener
import kotlin.math.E

class ExchangeFragment : Fragment() {

    lateinit var binding: FragmentExchangeBinding

    var hasFocus = false

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

        return binding.root
    }

    fun setListener() = with(binding) {
        layoutBaseCtr.singleClickListener {
            startSelector(Const.SELECTOR_TYPE_FIRST)
        }

        layoutChangeCtr.singleClickListener {
            startSelector(Const.SELECTOR_TYPE_FIRST)
        }


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

    private fun startSelector(type: String) {
        startActivity(
            Intent(requireContext(), Transparent::class.java)
                .apply {
                    putExtra(Const.SELECTOR_TYPE, type)
                }
        )
    }

    private fun setInputText(text: String) = with(binding) {
        amountEt.text = Editable.Factory.getInstance().newEditable("${amountEt.text}${text}")
    }

    private fun deleteText(isAll: Boolean) = with(binding) {
        val text = amountEt.text

        if (isAll) {
            text.delete(0, text.length)
        } else {
            text.delete(text.length-1, text.length)
        }

        amountEt.text = Editable.Factory.getInstance().newEditable(text)
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