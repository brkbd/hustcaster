package com.hustcaster.app.data

import org.xmlpull.v1.XmlPullParser


private const val IMAGE = "image"
private const val IMAGE_HREF = "href"
private const val AUTHOR = "author"
private const val DURATION = "duration"
private const val SUBTITLE = "subtitle"


class ItunesParser : FeedParser {
    override fun parse(xmlPullParser: XmlPullParser, state: FeedState) {
        val nodeName = xmlPullParser.name
        when (nodeName) {
            DURATION -> state.currentFeedItem?.duration = xmlPullParser.nextText()
            AUTHOR -> if (state.currentFeedItem == null) {
                state.feed.author = xmlPullParser.nextText()
            }

            SUBTITLE -> state.currentFeedItem?.subtitle = xmlPullParser.nextText()
            IMAGE -> if (state.currentFeedItem == null) {
                state.feed.imageUrl = xmlPullParser.getAttributeValue(null, IMAGE_HREF)
            }

        }
    }
}