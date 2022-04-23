package com.lzy.libview

import android.app.Application
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout


/**
 * Created by zhaoyang.li5 on 2022/4/13 8:04
 */
object LibViewInit {
    fun initRefresh(application: Application) {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { _, _ ->
            MaterialHeader(
                application
            )
        }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ ->
            //指定为经典Footer
            ClassicsFooter(context).setDrawableSize(20f)
        }
    }
}
