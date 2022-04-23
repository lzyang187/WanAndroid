package com.lzy.libview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * Created by zhaoyang.li5 on 2022/4/3 15:22
 */
abstract class BaseFragment<VB : ViewBinding> : Fragment(), IBaseView {

    protected open lateinit var mBinding: VB
    private var mIsFirstVisible = true
    private var mUserVisible = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mBinding = initViewBinding(inflater, container)
        initView()
        return mBinding.root
    }

    protected abstract fun initViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    protected open fun initView() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViewModel()
        if (!isHidden && userVisibleHint) {
            dispatchUserVisibleHint(true)
        }
    }

    protected open fun initViewModel() {

    }

    override fun onResume() {
        super.onResume()
        if (!mIsFirstVisible) {
            if (!isHidden && !mUserVisible && userVisibleHint) {
                dispatchUserVisibleHint(true)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (mUserVisible && userVisibleHint) {
            dispatchUserVisibleHint(false)
        }
    }

    private fun dispatchUserVisibleHint(visible: Boolean) {
        if (mUserVisible == visible) {
            return
        }
        mUserVisible = visible
        if (visible) {
            if (mIsFirstVisible) {
                mIsFirstVisible = false
                onVisible(true)
                requestOrLoadData()
            } else {
                onVisible(false)
            }
        } else {
            onInvisible()
        }
    }

    protected open fun requestOrLoadData() {

    }

    protected open fun onVisible(isFirstVisible: Boolean) {}

    protected open fun onInvisible() {}

    override fun isViewAttached(): Boolean {
        return context != null && isAdded
    }

    override fun toast(text: CharSequence?) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun toast(resId: Int) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show()
    }

}