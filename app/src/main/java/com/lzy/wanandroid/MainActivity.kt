package com.lzy.wanandroid

import androidx.viewpager2.widget.ViewPager2
import com.lzy.libview.BaseActivity
import com.lzy.libview.ViewPager2FragmentStateAdapter
import com.lzy.wanandroid.databinding.ActivityMainBinding
import com.lzy.wanandroid.ui.dashboard.DashboardFragment
import com.lzy.wanandroid.ui.home.HomeFragment
import com.lzy.wanandroid.ui.notifications.NotificationsFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val mHomeFragment = HomeFragment()
    private val mDashboardFragment = DashboardFragment()
    private val mNotificationsFragment = NotificationsFragment()

    override fun initViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        mBinding.viewPager.isUserInputEnabled = false
        mBinding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        mBinding.viewPager.adapter = ViewPager2FragmentStateAdapter(
            this, listOf(mHomeFragment, mDashboardFragment, mNotificationsFragment)
        )
        mBinding.navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    mBinding.viewPager.currentItem = 0
                }
                R.id.navigation_dashboard -> {
                    mBinding.viewPager.currentItem = 1
                }
                R.id.navigation_notifications -> {
                    mBinding.viewPager.currentItem = 2
                }
            }
            true
        }
    }
}