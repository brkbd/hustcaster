package com.hustcaster.app.data

import org.xmlpull.v1.XmlPullParser


private const val IMAGE = "image"
private const val IMAGE_HERF = "href"
private const val AUTHOR = "author"
private const val DURATION = "duration"
private const val SUBTITLE = "subtitle"
private const val ENCLOSURE = "enclosure"

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

            ENCLOSURE -> state.currentFeedItem?.audioUrl =
                xmlPullParser.getAttributeValue(null, "url")
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