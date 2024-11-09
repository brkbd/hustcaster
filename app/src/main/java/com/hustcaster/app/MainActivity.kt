package com.hustcaster.app

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hustcaster.app.compose.utils.FeedItemView
import com.hustcaster.app.data.FeedItem
import com.hustcaster.app.network.fetchRssData
import com.hustcaster.app.ui.theme.HustcasterTheme
import com.hustcaster.app.utils.convertStringToCalendar
import java.util.Calendar

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        fetchRssData("https://tomclarkscomicbookworld.libsyn.com/rss")
//        fetchRssData("https://feeds.megaphone.fm/GLT4787413333")

        setContent {
            HustcasterTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(name = "Android", modifier = Modifier.padding(innerPadding))
//                }
                Column {
                    Spacer(modifier = Modifier.padding(100.dp))
                    FeedItemView(
                        feedItem = FeedItem(
                            title = "Dodgeball",
                            pubDate = Calendar.getInstance(),
                            feedId = 1,
                            duration = "01:50:30"
                        ),
                        pictureUrl = "https://static.libsyn.com/p/assets/c/c/e/a/ccea20418e7aceed27a2322813b393ee/6M_Pod_New_Boink_Vertical_Logo_2024.png"
                    )
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HustcasterTheme {
        Greeting("Android")
    }
}