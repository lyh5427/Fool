package com.villains.fool.presentation.login

import androidx.lifecycle.ViewModel
import com.villains.fool.domain.DataRepositorySource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginModel @Inject constructor(
    private var repo: DataRepositorySource
) : ViewModel() {
    fun reqDuplicateCheck(snsID: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repo.reqDuplicateCheckSNSID(snsID)
        }
    }

    fun reqJoin(snsID: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repo.reqJoin(snsID)
        }
    }
}