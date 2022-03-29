package com.lzy.corebiz.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lzy.libhttp.result.RequestResult
import com.lzy.login.LoginRepository
import kotlinx.coroutines.launch

/**
 * @author: zyli44
 * @date: 2022/1/29 13:10
 * @description:
 */
class LoginViewModel : ViewModel() {
    private val mLoginRepository by lazy {
        LoginRepository()
    }

    fun login(name: String, pwd: String) {
        // Create a new coroutine on the UI thread
        // 此处仍需要协程，因为 makeLoginRequest 是一个 suspend 函数，而所有 suspend 函数都必须在协程中执行
        viewModelScope.launch {
            val jsonBody = "{ username: \"$name\", pwd: \"$pwd\"}"
            // 会挂起协程的进一步执行操作，直至 makeLoginRequest() 中的 withContext 块结束运行
            // withContext 块结束运行后，login() 中的协程在主线程上恢复执行操作，并返回网络请求的结果
            // 使用 try-catch 块处理 Repository 层可能抛出的异常
            val requestResult = try {
                mLoginRepository.makeLoginRequest(jsonBody)
            } catch (e: Exception) {
                RequestResult.Error(Exception("Network request failed"))
            }
            when (requestResult) {
                is RequestResult.Success -> {
                    // show success in UI
                }
                is RequestResult.Error -> {
                    // show error in UI
                }
            }

        }
    }
}
