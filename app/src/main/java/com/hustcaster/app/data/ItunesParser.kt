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
private val NEW_FEED_URL = "new-feed-url"

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
                state.feed.imageUrl = xmlPullParser.getAttributeValue(null, IMAGE_HERF)
            }
        }
    }
}
/*
* Standard RSS Tags:
Channel Information:

<title>: "Conversations with Tyler"
<link>: https://www.conversationswithtyler.com
<description>: Description of the podcast.
<pubDate>: Publication date of episodes.
<item>: Each episode entry with <title>, <description>, <link>, and <enclosure> for media files.
Media File Information:

<enclosure>: Holds the audio URL and type (e.g., audio/mpeg).
iTunes Tags:
Podcast Metadata:

<itunes:author>: Mercatus Center at George Mason University
<itunes:image>: Podcast/episode artwork URLs.
<itunes:category>: Podcast categories.
<itunes:explicit>: Whether the content is marked explicit.
Episode Metadata:

<itunes:title>: Episode-specific titles.
<itunes:duration>: Length of each episode.
<itunes:episode>: Episode number and type.
<itunes:keywords>: Keywords relevant to the episode.
<itunes:subtitle>: Short summary of the episode*/