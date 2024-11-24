package com.villains.fool.domain.di.dto.remote

sealed class Response() {
    class Success(val response: Any) : Response()
    class Fail(val err: String) : Response()
}