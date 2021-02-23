package com.codeandcore.test

import android.app.Application

class ShoppingApp : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this

    }

    companion object {
        var instance: ShoppingApp? = null
            private set
    }
}
