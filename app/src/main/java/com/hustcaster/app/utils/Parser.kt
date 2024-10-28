package com.hustcaster.app.utils

import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader

private val factory: XmlPullParserFactory = XmlPullParserFactory.newInstance()
private val xmlPullParser: XmlPullParser = factory.newPullParser()


fun parseXML(xmlData: String) {
    try {
        var title = ""
        xmlPullParser.setInput(StringReader(xmlData))
        var eventType = xmlPullParser.eventType
        while (eventType != XmlPullParser.END_DOCUMENT) {
            val nodeName = xmlPullParser.name
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    if(nodeName=="title"){
                        title=xmlPullParser.nextText()
                        Log.d("parser",title)
                    }
                }
            }
            eventType = xmlPullParser.next()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}