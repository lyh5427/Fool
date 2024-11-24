package com.villains.fool.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.villains.fool.Application
import com.villains.fool.domain.DataRepositorySource
import com.villains.fool.domain.di.dto.remote.LoginVo
import com.villains.fool.domain.di.dto.remote.OnResponse
import com.villains.fool.domain.di.dto.remote.ReqJoinVo
import com.villains.fool.domain.di.dto.remote.Response
import com.villains.fool.domain.di.dto.remote.RspLoginVo
import com.villains.fool.presentation.Action
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginModel @Inject constructor(
    private var repo: DataRepositorySource
) : ViewModel() {

    private var _action: MutableSharedFlow<String> =
        MutableSharedFlow(0, 1, BufferOverflow.DROP_OLDEST)
    val action = _action.asSharedFlow()


    // 중복 아이디 있을 시 에러메시지
    fun reqDuplicateCheck() {
        CoroutineScope(Dispatchers.IO).launch {
            val request = ReqJoinVo(
                idToken = Application.prefs.idToken,
                userEmail = Application.prefs.snsId,
                snsDivCd = "google",
                snsId = Application.prefs.snsId
            )

            repo.reqDuplicateCheckSNSID(Application.prefs.snsId).collect {
                when (it) {
                    is Response.Success -> {
                        reqJoin()
                    }

                    is Response.Fail -> {

                    }
                }
            }
        }
    }

    fun reqJoin() {
        CoroutineScope(Dispatchers.IO).launch {
            repo.reqJoin(Application.prefs.snsId).collect {
                when (it) {
                    is Response.Success -> {
                        reqLogin()
                    }

                    is Response.Fail -> {
                        Log.d("Tag", "Response: ${it.err}")
                    }
                }

            }
        }
    }

    fun reqLogin() {
        CoroutineScope(Dispatchers.IO).launch {
            repo.reqLogin(Application.prefs.snsId).collect {
                when (it) {
                    is Response.Success -> {

                        val typeToken = object : TypeToken<OnResponse<RspLoginVo>>() {}.type
                        val responseData = Gson().fromJson<OnResponse<RspLoginVo>>( it.response as String, typeToken)

                        Application.prefs.accessToken = responseData.data!!.accessToken!!
                        Application.prefs.isLogin = true

                        _action.emit(Action.START_MAIN)
                    }

                    is Response.Fail -> {
                        Log.d("Tag", "Response: ${it.err}")
                    }
                }
            }
        }
    }
}