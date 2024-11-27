package com.villains.fool.presentation.main.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.villains.fool.R
import com.villains.fool.databinding.FragmentHomeBinding
import com.villains.fool.presentation.main.home.adapter.MainFragmentAdapter
import com.villains.fool.presentation.main.home.fragment.ExchangeDataFragment
import com.villains.fool.presentation.main.home.fragment.ExchangeFragment
import com.villains.fool.presentation.mypage.MyPageActivity
import com.villains.fool.singleClickListener

class Home : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var exchanger: ExchangeFragment
    lateinit var history: ExchangeDataFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        setTabAdapter()
        setListener()
        return binding.root
    }

    private fun setTabAdapter() = with(binding) {

        exchanger = ExchangeFragment()
        history = ExchangeDataFragment()

        val adapter = MainFragmentAdapter(this@Home).apply {
            addFragment(exchanger)
            addFragment(history)
        }
        homeViewPager.adapter = adapter

        TabLayoutMediator(layoutTab, homeViewPager) {tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.exchange_title)
                else -> getString(R.string.hitory_title)
            }
        }.attach()

        layoutTab.selectTab(layoutTab.getTabAt(0))
    }

    private fun setListener() = with(binding) {
        myPage.singleClickListener {
            startActivity(Intent(requireContext(), MyPageActivity::class.java))
        }
    }
}