package com.aman.base.helper.view.adapters.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    private val fragments = ArrayList<Fragment>()
    private val titles = ArrayList<String>()

    fun addFragment(fragment: Fragment, title: String) {
        fragments.add(fragment)
        titles.add(title)

        notifyDataSetChanged()
    }

    fun removeFragment(position: Int) {
        fragments.removeAt(position)
        titles.removeAt(position)

        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    companion object {
        val TAG: String = ViewPagerAdapter::class.java.simpleName
    }
}
