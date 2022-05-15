package com.lzy.wanandroid.collect

import androidx.lifecycle.MutableLiveData
import com.lzy.corebiz.BaseRequestViewModel
import com.lzy.corebiz.httpservice.bean.ArticleBean
import com.lzy.corebiz.login.UserMgr
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by zhaoyang.li5 on 2022/5/15 19:12
 */
@HiltViewModel
open class BaseCollectViewModel @Inject constructor() : BaseRequestViewModel() {

    @Inject
    lateinit var mCollectRepository: CollectRepository

    private val mNotifyPosition = MutableLiveData<Int>()
    val getNotifyPosition = mNotifyPosition

    fun collectOrNot(position: Int, data: ArticleBean) {
        data.id?.let { id: Int ->
            if (data.collect == true) {
                // 取消收藏
                unCollect(position, data)
            } else {
                // 收藏
                data.collect = true
                launchToastHandlerRequest {
                    val result = mCollectRepository.collectArticle(id)
                    when {
                        result.success() -> {
                            data.collect = true
                            // 更新页面
                            mNotifyPosition.value = position
                        }
                        result.needLogin() -> {
                            // 清除本地用户信息
                            UserMgr.logOut()
                            mNeedLoginLiveData.value = true
                            mToastLiveData.value = result.errorMsg
                        }
                        else -> {
                            mToastLiveData.value = result.errorMsg
                        }
                    }
                }
            }
        }
    }

    private fun unCollect(position: Int, data: ArticleBean) {
        launchToastHandlerRequest {
            data.id?.let {
                val result = if (data.originId != null) {
                    mCollectRepository.unCollectArticleInCollectPage(it, data.originId!!)
                } else {
                    mCollectRepository.unCollectArticle(it)
                }
                when {
                    result.success() -> {
                        data.collect = false
                        // 更新页面
                        mNotifyPosition.value = position
                    }
                    result.needLogin() -> {
                        // 清除本地用户信息
                        UserMgr.logOut()
                        mNeedLoginLiveData.value = true
                        mToastLiveData.value = result.errorMsg
                    }
                    else -> {
                        mToastLiveData.value = result.errorMsg
                    }
                }
            }
        }
    }
}
