package com.lzy.libview.banner

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.util.LogUtils
import com.lzy.libview.BaseAdapter
import com.lzy.libview.R

/**
 * Created by zhaoyang.li5 on 2022/4/25 19:15
 */
class BannerView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    private val mViewPager2 by lazy {
        findViewById<ViewPager2>(R.id.view_pager2)
    }

    private val mHandler by lazy {
        Handler(Looper.getMainLooper(), Handler.Callback {
            return@Callback if (it.what == INTERVAL_MESSAGE) {
                LogUtils.iTag(TAG, "收到轮播消息：$mCurIndex")
                mCurIndex++
                mViewPager2.currentItem = mCurIndex
                true
            } else {
                false
            }
        })
    }

    private var mCurIndex = 0

    init {
        LayoutInflater.from(context).inflate(R.layout.lib_view_banner_view_layout, this, true)
        mViewPager2.isUserInputEnabled = true
        mViewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        mViewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                mCurIndex = position
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                LogUtils.iTag(TAG, "onPageScrollStateChanged = $state")
                if (state == ViewPager2.SCROLL_STATE_DRAGGING) {
                    stopInterval()
                } else if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    startInterval()
                }
            }
        })
    }

    private lateinit var mAdapter: BannerAdapter

    fun setBannerData(
        bannerList: List<IBannerData>,
        itemClickListener: BaseAdapter.OnItemClickListener<IBannerData>?
    ) {
        if (bannerList.isNotEmpty()) {
            if (::mAdapter.isInitialized) {
                mAdapter.notifyDataSetChanged()
            } else {
                mAdapter = BannerAdapter(context, bannerList, itemClickListener)
                mViewPager2.adapter = mAdapter
            }
            mViewPager2.setCurrentItem(Int.MAX_VALUE / 2, false)
            startInterval()
        }
    }

    /**
     * 开始轮播
     */
    private fun startInterval() {
        mHandler.removeMessages(INTERVAL_MESSAGE)
        mHandler.sendEmptyMessageDelayed(INTERVAL_MESSAGE, INTERVAL_DURATION)
    }

    private fun stopInterval() {
        mHandler.removeMessages(INTERVAL_MESSAGE)
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        LogUtils.iTag(TAG, "onWindowVisibilityChanged：$visibility")
        super.onWindowVisibilityChanged(visibility)
        if (visibility == VISIBLE) {
            startInterval()
        } else {
            stopInterval()
        }
    }

    companion object {
        private const val TAG = "BannerView"
        private const val INTERVAL_DURATION = 5000L
        private const val INTERVAL_MESSAGE = 10
    }

}