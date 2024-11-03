package com.villains.fool.presentation.main.home.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainFragmentAdapter(fragmentActivity: Fragment) : FragmentStateAdapter(fragmentActivity) {
    var fragmentList = ArrayList<Fragment>()

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

    fun addFragment(frag: Fragment) {
        fragmentList.add(frag)
        notifyItemInserted(fragmentList.size - 1)
    }
}