package com.willyweather.assignment.di

import com.willyweather.assignment.BuildConfig
import com.willyweather.assignment.data.remote.GithubService
import com.willyweather.assignment.data.Repository
import com.willyweather.assignment.data.local.GithubDb
import com.willyweather.assignment.ui.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.github.com/"

@ExperimentalCoroutinesApi
val appModule = module {

    single {
        GithubDb.create(androidContext(), false)
    }

    single {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    single {

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create(GithubService::class.java)
    }

    single {
        Repository(get(), get())
    }

    viewModel {
        MainViewModel(get())
    }

}