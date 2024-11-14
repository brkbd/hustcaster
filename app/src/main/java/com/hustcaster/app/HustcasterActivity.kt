package com.hustcaster.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.hustcaster.app.compose.HustcasterApp
import com.hustcaster.app.ui.theme.HustcasterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HustcasterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HustcasterTheme {
                HustcasterApp()
            }
        }
    }
}