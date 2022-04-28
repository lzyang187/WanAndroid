package com.lzy.wanandroid

import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.viewpager2.widget.ViewPager2
import com.lzy.libview.BaseActivity
import com.lzy.libview.ViewPager2FragmentStateAdapter
import com.lzy.wanandroid.databinding.ActivityMainBinding
import com.lzy.wanandroid.ui.dashboard.DashboardFragment
import com.lzy.wanandroid.ui.home.HomeFragment
import com.lzy.wanandroid.ui.notifications.NotificationsFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    @Inject
    lateinit var mHomeFragment: HomeFragment

    @Inject
    lateinit var mDashboardFragment: DashboardFragment

    @Inject
    lateinit var mNotificationsFragment: NotificationsFragment

    override fun initViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        initToolBar()
        mBinding.viewPager.isUserInputEnabled = false
        mBinding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        mBinding.viewPager.adapter = ViewPager2FragmentStateAdapter(
            this, listOf(mHomeFragment, mDashboardFragment, mNotificationsFragment)
        )
        mBinding.bottomNavView.setOnItemSelectedListener { item ->
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
        // 抽屉布局
        mBinding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.my_collect -> {
                    toast(R.string.my_collect)
                }
                R.id.my_share -> {
                    toast(R.string.my_share)
                }
            }
            mBinding.drawerLayout.closeDrawers()
            return@setNavigationItemSelectedListener true
        }
    }

    private fun initToolBar() {
        setSupportActionBar(mBinding.toolbarLayout.toolbar)
        mBinding.toolbarLayout.toolbar.setTitleTextAppearance(
            this, com.lzy.libview.R.style.lib_view_toolbar_title
        )
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                mBinding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        return true
    }


}