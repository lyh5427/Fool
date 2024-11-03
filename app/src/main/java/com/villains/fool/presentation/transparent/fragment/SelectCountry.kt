package com.villains.fool.presentation.transparent.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import com.villains.fool.databinding.FragmentSelectcountryBinding
import com.villains.fool.presentation.transparent.Transparent
import com.villains.fool.singleClickListener

private const val TITLE = "title"
private const val BTN_TEXT = "btn_text"
private const val TYPE = "type"

class SelectCountry : Fragment() {

    lateinit var binding: FragmentSelectcountryBinding
    lateinit var title: String
    lateinit var btnTxt: String
    lateinit var type: String

    var flag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectcountryBinding.inflate(inflater, container, false)

        binding.motion.transitionToEnd()

        binding.selectClose.singleClickListener {

        }

        binding.motion.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {

            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {

            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {

            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                if (binding.motion.progress == 0.0f) flag = true

                if (flag && binding.motion.progress == 1.0f) {
                    (requireActivity() as Transparent).finish()
                }
            }
        })


        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(title:String, backBtnTxt: String, type: String) =
            SelectCountry().apply {
                arguments = Bundle().apply {
                    putString(TITLE, title)
                    putString(BTN_TEXT, backBtnTxt)
                    putString(TYPE, type)
                }
            }
    }
}