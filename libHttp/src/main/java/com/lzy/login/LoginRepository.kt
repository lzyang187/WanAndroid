package com.lzy.login

import com.lzy.libhttp.result.RequestResult
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author: zyli44
 * @date: 2022/1/29 13:01
 * @description: 登录的接口请求
 */
class LoginRepository {
    companion object {
        private const val LOGIN_URL = "https://example.com/login"
    }

    suspend fun makeLoginRequest(jsonBody: String): RequestResult<LoginResponse> {
        // withContext(Dispatchers.IO) 将协程的执行操作移至一个 I/O 线程
        return withContext(Dispatchers.IO) {
            val url = URL(LOGIN_URL)
            (url.openConnection() as? HttpURLConnection)?.run {
                requestMethod = "POST"
                setRequestProperty("Content-Type", "application/json; utf-8")
                setRequestProperty("Accept", "application/json")
                doOutput = true
                outputStream.write(jsonBody.toByteArray())
                return@run RequestResult.Success(LoginResponse())
            }
            return@withContext RequestResult.Error(Exception("Cannot open HttpURLConnection"))
        }
    }
}
