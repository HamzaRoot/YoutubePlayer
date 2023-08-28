package com.example.gracetechnologiestest.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter

class ViewPagerStatAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val mFragmentList = ArrayList<Fragment>()
    private var pageRefreshState = PagerAdapter.POSITION_UNCHANGED

    fun refreshStateSet(isRefresh: Boolean) {
        pageRefreshState = if (isRefresh) {
            PagerAdapter.POSITION_NONE
        } else {
            PagerAdapter.POSITION_UNCHANGED
        }
    }

    fun addFragment(fragment: Fragment) {
        mFragmentList.add(fragment)
    }

    fun removeFragment(position: Int) {
        mFragmentList.removeAt(position)
        notifyDataSetChanged()
    }

    override fun getItemPosition(`object`: Any): Int {
        // refresh all fragments when data set changed
        return pageRefreshState
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }
}