package com.hustcaster.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.navigation.NavController
import com.hustcaster.app.utils.SafeCoroutineScope
import com.hustcaster.app.utils.SafeCoroutineScopeImpl
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        applicationScope=SafeCoroutineScope(SupervisorJob())
        context = this.applicationContext
    }

    companion object {
        lateinit var applicationScope: CoroutineScope

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
}