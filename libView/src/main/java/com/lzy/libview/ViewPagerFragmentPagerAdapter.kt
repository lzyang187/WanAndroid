package com.lzy.libview

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * Created by zhaoyang.li5 on 2022/4/11 11:06
 */
class ViewPagerFragmentPagerAdapter(
    fm: FragmentManager, private val mFragmentList: List<BaseFragment<*>>
) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }
}