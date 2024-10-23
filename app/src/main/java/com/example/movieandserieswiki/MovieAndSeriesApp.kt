package com.example.movieandserieswiki

import android.app.Application
import com.example.movieandserieswiki.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MovieAndSeriesApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@MovieAndSeriesApp)
            androidLogger()
            modules(appModule)

        }

    }
}