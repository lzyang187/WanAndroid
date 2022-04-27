package com.lzy.wanandroid.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.lzy.libview.BaseFragment
import com.lzy.wanandroid.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment @Inject constructor() : BaseFragment<FragmentDashboardBinding>() {

    private val dashboardViewModel: DashboardViewModel by viewModels()

    override fun initViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentDashboardBinding {
        return FragmentDashboardBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        val textView: TextView = mBinding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
    }
}