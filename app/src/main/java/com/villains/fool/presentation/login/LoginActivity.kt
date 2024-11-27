package com.villains.fool.presentation.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.villains.fool.Application
import com.villains.fool.Application.Companion.TAG
import com.villains.fool.databinding.ActivityLoginBinding
import com.villains.fool.presentation.Action
import com.villains.fool.presentation.main.MainActivity
import com.villains.fool.singleClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    val model: LoginModel by viewModels()
    lateinit var googleIdTokenCredential: GoogleIdTokenCredential

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListener()
        lifecycleScope.launch { setObserver() }
    }

    override fun onResume() {
        super.onResume()

        startLogin()
    }

    private suspend fun setObserver() = with(binding) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            model.action.collect {
                when (it) {
                    Action.START_MAIN -> {
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java)
                            .apply {
                                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            })
                    }
                }
            }
        }
    }

    private fun setListener() = with(binding) {
        layoutLogin.singleClickListener {
            googleLogin()
//            model.reqExchageRate(Application.prefs.idToken)
        }
    }

    private fun startLogin() {
        if (Application.prefs.isLogin) {
            model.reqLogin()
        }
    }

    fun googleLogin() {
        val credentialManager = CredentialManager.create(this@LoginActivity)

        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId("985329621446-dh994hdjr1v764r973o1hpq3gblapju8.apps.googleusercontent.com")
            .setAutoSelectEnabled(true)
            .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = this@LoginActivity,
                )
                handleSignIn(result)
            } catch (e: GetCredentialException) {
                Log.e(TAG, "Error : ${e.message}")
            }
        }
    }

    fun handleSignIn(result: GetCredentialResponse) {
        val credential = result.credential

        when (credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)

                        googleIdTokenCredential

                        Application.prefs.idToken = googleIdTokenCredential.idToken
                        Application.prefs.snsId = googleIdTokenCredential.id

                        Log.d(TAG, "Token : ${googleIdTokenCredential.idToken} \n " +
                                "pNum : ${googleIdTokenCredential.phoneNumber} \n" +
                                "id : ${googleIdTokenCredential.id} \n" +
                                "familyName : ${googleIdTokenCredential.familyName} \n" +
                                "given Name : ${googleIdTokenCredential.givenName} \n" +
                                "display Name : ${googleIdTokenCredential.displayName} ")
                        model.reqDuplicateCheck(this@LoginActivity)

//                        model.reqJoin(googleIdTokenCredential.id)
//                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))

                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e(TAG, "Received an invalid google id token response", e)
                    }
                }
                else {
                    Log.e(TAG, "Unexpected type of credential")
                }
            }

            else -> {
                Log.e(TAG, "Unexpected type of credential")
            }
        }
    }
}