package com.hustcaster.app.network.parser

import android.util.Log
import com.hustcaster.app.data.model.Episode
import com.hustcaster.app.data.model.Podcast
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
            DURATION -> currentItem?.duration = xmlPullParser.nextText()
            AUTHOR -> if (currentItem == null) {
                podcast.author = xmlPullParser.nextText()
            }

            IMAGE -> if (currentItem == null) {
                podcast.imageUrl = xmlPullParser.getAttributeValue(null, IMAGE_HREF)
            }

        }
    }
}
//Duration of the episode, in one of the following formats:
//
//hours:minutes:seconds
//Example: <itunes:duration>1:10:00</itunes:duration>
//minutes:seconds
//Example: <itunes:duration>10:00</itunes:duration>
//total_seconds
//Example: <itunes:duration>1800</itunes:duration>
//In the first two formats the values for hours, minutes, or seconds cannot exceed two digits each.