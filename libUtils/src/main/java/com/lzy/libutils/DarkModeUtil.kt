package com.lzy.libutils

import android.content.Context
import android.content.res.Configuration

/**
 * Created by zhaoyang.li5 on 2022/4/25 11:58
 */
object DarkModeUtil {
    fun isDarkMode(context: Context): Boolean {
        return isDarkMode(context.resources.configuration)
    }

    private fun isDarkMode(config: Configuration): Boolean {
        val uiMode = config.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return uiMode == Configuration.UI_MODE_NIGHT_YES
    }

}