package com.villains.fool.presentation.transparent.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import com.villains.fool.Application
import com.villains.fool.databinding.FragmentSelectcountryBinding
import com.villains.fool.presentation.Const
import com.villains.fool.presentation.transparent.Transparent
import com.villains.fool.singleClickListener
import java.util.logging.Handler

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
            title = it.getString(TITLE, "")
            btnTxt = it.getString(BTN_TEXT, "")
            type = it.getString(TYPE, Const.SELECTOR_TYPE_FIRST)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectcountryBinding.inflate(inflater, container, false)

        setListener()
        setMotion()

        return binding.root
    }

    private fun setMotion() {

        binding.motion.transitionToEnd()

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

    }

    private fun setListener() = with(binding) {

        selectClose.singleClickListener {
            motion.transitionToEnd()
        }

        layoutKr.singleClickListener { selectCountry(Const.KOREA) }

        layoutEU.singleClickListener { selectCountry(Const.EUROPE) }

        layoutAus.singleClickListener { selectCountry(Const.AUSTRALIA) }

        layoutViet.singleClickListener { selectCountry(Const.VIETNAM) }

        layoutUSA.singleClickListener { selectCountry(Const.USA) }

        layoutSing.singleClickListener { selectCountry(Const.SINGAPORE) }

        layoutChina.singleClickListener { selectCountry(Const.CHINA) }

        layoutThail.singleClickListener { selectCountry(Const.THAILAND) }

        layoutTaiwan.singleClickListener { selectCountry(Const.TAIWAN) }

        layoutJapan.singleClickListener { selectCountry(Const.JAPAN) }
    }

    private fun selectCountry(country: String) {
        when (type) {
            Const.SELECTOR_TYPE_FIRST -> {
                Application.prefs.baseCountry = country
                binding.motion.transitionToEnd()
            }

            else -> {
                Application.prefs.exchangeCountry = country
                binding.motion.transitionToEnd()
            }
        }
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