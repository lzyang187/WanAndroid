package com.lzy.wanandroid.ui.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lzy.libview.BaseFragment
import com.lzy.wanandroid.databinding.FragmentNotificationsBinding

class NotificationsFragment : BaseFragment<FragmentNotificationsBinding>() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun initViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentNotificationsBinding {
        return FragmentNotificationsBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        notificationsViewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)
        val textView: TextView = mBinding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
    }
}