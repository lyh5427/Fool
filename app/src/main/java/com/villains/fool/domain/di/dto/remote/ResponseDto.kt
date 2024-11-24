package com.villains.fool.domain.di.dto.remote

data class ExchangeRateVo(
    var exgrId: String = "",
    var dtSrno: Int = 0,
    var ctrDivCd: String = "",
    var exgrTime: String = "",
    var exgrBaseRate: Double = 0.0,
    var ttb: Double = 0.0,
    var tts: Double = 0.0
)

data class OnResponse <T>(
    var code: String,
    var message: String,
    var data: T?,
    var timestamp: String
)

data class RspLoginVo(
    var refreshToken: String? = null,
    var accessToken: String? = null,
)