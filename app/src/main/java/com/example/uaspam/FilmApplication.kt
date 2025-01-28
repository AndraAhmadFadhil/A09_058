package com.example.uaspam

import android.app.Application
import com.example.uaspam.di.AppContainer
import com.example.uaspam.di.FilmContainer

class FilmApplication: Application() {

    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = FilmContainer()
    }
}