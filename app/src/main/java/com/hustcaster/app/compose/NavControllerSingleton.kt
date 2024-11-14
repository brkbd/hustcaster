package com.hustcaster.app.compose

import android.annotation.SuppressLint
import androidx.navigation.NavHostController
import javax.inject.Singleton

@Singleton
class NavControllerSingleton {
    companion object{
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: NavHostController?=null
        fun getInstance(navController: NavHostController)=
            instance?: synchronized(this){
                instance?:navController.also { instance=it }
            }
    }

}