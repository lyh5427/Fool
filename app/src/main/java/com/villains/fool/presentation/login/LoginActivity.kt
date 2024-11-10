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
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.villains.fool.Application.Companion.TAG
import com.villains.fool.databinding.ActivityLoginBinding
import com.villains.fool.presentation.main.MainActivity
import com.villains.fool.singleClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
    }

    fun setListener() = with(binding) {
        layoutLogin.singleClickListener {
            googleLogin()
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

                        Log.d(TAG, "Token : ${googleIdTokenCredential.idToken} \n " +
                                "pNum : ${googleIdTokenCredential.phoneNumber} \n" +
                                "id : ${googleIdTokenCredential.id} \n" +
                                "familyName : ${googleIdTokenCredential.familyName} \n" +
                                "given Name : ${googleIdTokenCredential.givenName} \n" +
                                "display Name : ${googleIdTokenCredential.displayName} ")

//                        model.reqJoin(googleIdTokenCredential.id)
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))

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