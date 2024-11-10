package com.villains.fool.domain.di.dto.remote

data class JoinBody(
    // userBas
    val userEmail: String = "",
    val snsDivCd: String = "",
    val snsId: String = "",
    // userDtl
    val userNm: String = "",
    val ctrDivCd: String = "",
    val sexDivCd: String = "",
    // envDts
    val appVerVal: String = "",
    val langDivCd: String = "",
    val devcNm: String = "",
    val devcImei: String = "",
    val fcmToken: String = ""
)

data class DuplicateCheckBobdy<T>(
    val code: String,
    val message: String,
    val data: T,
    val timestamp: String
)

data class Test(
    var snsId: String = "aaaa"
)