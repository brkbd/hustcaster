package com.hustcaster.app.network.parser

import android.util.Log
import com.hustcaster.app.data.model.Episode
import com.hustcaster.app.data.model.Podcast
import com.hustcaster.app.utils.convertDurationStringToLong
import org.xmlpull.v1.XmlPullParser

private const val IMAGE = "image"
private const val IMAGE_HREF = "href"
private const val AUTHOR = "author"
private const val DURATION = "duration"

class ItunesParser : FeedParser {
    override fun parse(
        xmlPullParser: XmlPullParser,
        podcast: Podcast,
        currentItem: Episode?
    ) {
        val nodeName = xmlPullParser.name
        when (nodeName) {
            DURATION -> currentItem?.duration =
                convertDurationStringToLong(xmlPullParser.nextText())

            AUTHOR -> if (currentItem == null) {
                podcast.author = xmlPullParser.nextText()
            } else {
                currentItem.author = xmlPullParser.nextText()
            }

            IMAGE -> if (currentItem == null) {
                podcast.imageUrl = xmlPullParser.getAttributeValue(null, IMAGE_HREF)
            } else {
                currentItem.imageUrl = xmlPullParser.getAttributeValue(null, IMAGE_HREF)
            }

        }
    }
}