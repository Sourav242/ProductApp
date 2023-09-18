package com.example.productapp

import android.app.Application
import com.example.productapp.base.di.ProductApplicationModule
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ProductApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ProductApplicationModule.application = this
    }
}