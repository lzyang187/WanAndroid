package com.lzy.wanandroid.ui.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.lzy.libview.BaseFragment
import com.lzy.wanandroid.databinding.FragmentNotificationsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationsFragment @Inject constructor() : BaseFragment<FragmentNotificationsBinding>() {

    private val notificationsViewModel: NotificationsViewModel by viewModels()

    override fun initViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentNotificationsBinding {
        return FragmentNotificationsBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        val textView: TextView = mBinding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
    }
}