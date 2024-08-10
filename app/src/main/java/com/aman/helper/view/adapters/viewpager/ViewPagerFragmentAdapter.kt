package com.aman.helper.view.adapters.viewpager

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

open class ViewPagerFragmentAdapter(
    activity: AppCompatActivity,
    private val fragments: ArrayList<Fragment>
) : FragmentStateAdapter(activity) {
    open val TAG = ViewPagerFragmentAdapter::class.java.simpleName

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}