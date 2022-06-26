package com.lzy.wanandroid

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.util.ActivityUtils
import com.bumptech.glide.Glide
import com.lzy.corebiz.httpservice.bean.UserBean
import com.lzy.corebiz.login.UserMgr
import com.lzy.corebiz.login.ui.login.LoginActivity
import com.lzy.libbasefunction.glide.GlideHelper
import com.lzy.libview.BaseActivity
import com.lzy.libview.ViewPager2FragmentStateAdapter
import com.lzy.wanandroid.collect.CollectArticlesActivity
import com.lzy.wanandroid.databinding.ActivityMainBinding
import com.lzy.wanandroid.databinding.DrawerHeaderBinding
import com.lzy.wanandroid.settings.SettingsActivity
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
                    if (UserMgr.isLogin()) {
                        ActivityUtils.startActivity(
                            Intent(
                                this, CollectArticlesActivity::class.java
                            )
                        )
                    } else {
                        LoginActivity.startLoginActivity(this)
                    }
                }
                R.id.my_share -> {
                    toast(R.string.my_share)
                }
                R.id.settings -> {
                    ActivityUtils.startActivity(Intent(this, SettingsActivity::class.java))
                }
            }
            return@setNavigationItemSelectedListener true
        }
        initDrawHeaderView()
    }

    private lateinit var mDrawerHeaderBinding: DrawerHeaderBinding

    private fun initDrawHeaderView() {
        mDrawerHeaderBinding = DrawerHeaderBinding.bind(mBinding.navigationView.getHeaderView(0))
        val userBean = UserMgr.getUserBeanLiveData.value
        displayLoginStatus(userBean)
        UserMgr.getUserBeanLiveData.observe(this, Observer {
            displayLoginStatus(it)
        })
    }

    private fun displayLoginStatus(userBean: UserBean?) {
        if (userBean != null) {
            GlideHelper.load(
                Glide.with(this),
                userBean.icon,
                mDrawerHeaderBinding.iconImage,
                GlideHelper.getCircleRequestOptions(mDrawerHeaderBinding.iconImage)
            )
            mDrawerHeaderBinding.nameText.text = userBean.nickname
            mDrawerHeaderBinding.emailText.visibility = View.VISIBLE
            mDrawerHeaderBinding.emailText.text = userBean.email
            mDrawerHeaderBinding.root.setOnClickListener(null)
        } else {
            mDrawerHeaderBinding.nameText.setText(R.string.core_biz_action_go_sign_in)
            mDrawerHeaderBinding.emailText.visibility = View.GONE
            mDrawerHeaderBinding.root.setOnClickListener {
                LoginActivity.startLoginActivity(this)
            }
        }
    }

    private fun initToolBar() {
        setSupportActionBar(mBinding.toolbarLayout.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                mBinding.drawerLayout.openDrawer(GravityCompat.START)
            }
            R.id.search -> {
                toast(R.string.search)
            }
        }
        return true
    }


}