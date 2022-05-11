package com.lzy.libview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by zhaoyang.li5 on 2022/4/17 17:34
 */
open class BaseViewModel : ViewModel() {
    protected val mToastLiveData = MutableLiveData<String>()
    val getToastLiveData = mToastLiveData
}