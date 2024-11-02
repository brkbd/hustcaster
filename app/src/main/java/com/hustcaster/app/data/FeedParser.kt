package com.hustcaster.app.data

import org.xmlpull.v1.XmlPullParser

interface FeedParser {
    fun parse(xmlPullParser: XmlPullParser, state: FeedAndFeedItems);
}