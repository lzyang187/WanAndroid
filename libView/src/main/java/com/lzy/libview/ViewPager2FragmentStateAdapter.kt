package com.lzy.libview

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Created by zhaoyang.li5 on 2022/2/24 14:13
 */
class ViewPager2FragmentStateAdapter(
    fragmentActivity: FragmentActivity, private val mFragmentList: List<BaseFragment<*>>
) : FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getItemCount(): Int {
        return mFragmentList.size
    }
}