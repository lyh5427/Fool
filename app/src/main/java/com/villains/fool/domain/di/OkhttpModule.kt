package com.villains.fool.domain.di

import android.content.Context
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object OkHttpModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(AddCookiesInterceptor(context))
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

    @Singleton
    @Provides
    fun provideOkHttpRequest() : Request =
        Request.Builder()
            .url("")
            .build()
}

class AddCookiesInterceptor(private val context: Context) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val builder = chain.request().newBuilder()

        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            .getStringSet(PREF_COOKIES, HashSet()) as HashSet<String>

        for (cookie in preferences) {
            builder.addHeader("Cookie", cookie)
        }

        return chain.proceed(builder.build())
    }

    companion object {
        val PREF_COOKIES = "PREF_COOKIES"
    }
}

class ReceivedCookiesInterceptor(val context: Context): Interceptor { // AddCookiesInterceptor()

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val originalResponse = chain.proceed(chain.request())

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            val cookies = PreferenceManager.getDefaultSharedPreferences(context)
                .getStringSet("PREF_COOKIES", HashSet()) as HashSet<String>

            for (header in originalResponse.headers("Set-Cookie")) {
                cookies.add(header)
            }

            val memes = PreferenceManager.getDefaultSharedPreferences(context).edit()
            memes.putStringSet("PREF_COOKIES", cookies).apply()
            memes.commit()
        }

        return originalResponse
    }
}