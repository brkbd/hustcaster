package com.hustcaster.app.data

import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader

private val NSTAG = "itunes"
private val NSURI = "http://www.itunes.com/dtds/podcast-1.0.dtd"
private val IMAGE = "image"
private val IMAGE_HERF = "href"
private val AUTHOR = "author"
private val DURATION = "duration"
private val SUBTITLE = "subtitle"
private val SUMMARY = "summary"
private val NEW_FEED_URL = "new-feed-url"

class ItunesParser : FeedParser {
    override fun parse(xmlPullParser: XmlPullParser) {
        try {
            var title = ""
            var eventType = xmlPullParser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                val nodeName = xmlPullParser.name
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        if (nodeName == "title") {
                            title = xmlPullParser.nextText()
                            Log.d("parser", title)
                        }
                    }
                }
                eventType = xmlPullParser.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}