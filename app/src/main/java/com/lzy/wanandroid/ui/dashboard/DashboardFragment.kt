package com.lzy.wanandroid.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lzy.libview.BaseFragment
import com.lzy.wanandroid.databinding.FragmentDashboardBinding

class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun initViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentDashboardBinding {
        return FragmentDashboardBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        val textView: TextView = mBinding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
    }
}